/**
 * 
 */
package sk.qbsw.core.pay.base.csob;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import sk.qbsw.core.pay.base.Payment;
import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentRealization;
import sk.qbsw.core.pay.base.csob.model.CsobBankResponse;
import sk.qbsw.core.pay.base.csob.model.CsobEnvelope;
import sk.qbsw.core.pay.base.csob.model.CsobFormPayRequest;
import sk.qbsw.core.pay.base.csob.model.CsobGetCSOBResponce;
import sk.qbsw.core.pay.base.csob.model.CsobPayRequest;
import sk.qbsw.core.pay.base.csob.model.CsobResponseFromVOD;
import sk.qbsw.core.pay.base.exception.ConfigurationException;
import sk.qbsw.core.pay.base.exception.DecryptionException;
import sk.qbsw.core.pay.base.response.AbstractBankResponse;
import sk.qbsw.core.pay.base.util.PaymentFormatUtils;

/**
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class CsobPaymentProcessor extends PaymentProcessor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CsobPaymentProcessor.class);

	private static final String PARAM_NAME_OBJ_ID = "id";
	private CsobInitParams context;

	/**
	 * @param context
	 */
	public CsobPaymentProcessor (CsobInitParams context)
	{
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.dockie.core.payment.paymentProcessor.PaymentProcessor#createPayment(sk.qbsw.dockie.core.payment.paymentProcessor.Payment)
	 */
	@Override
	public PaymentRealization createPayment (Payment payment)
	{

		PaymentRealization payments = new PaymentRealization();
		CsobPayRequest pay = new CsobPayRequest();
		pay.setMerchantId(context.getMerchantId());
		pay.setMerchantAccount(context.getMerchantAccountNumber());
		pay.setMerchantBankNo(context.getMerchantBankNumber());
		pay.setSum(normalizeAmountAndConvert(payment.getAmount()));

		//lebo csob nepodporuje ziadne IDcko platby a vo vysledku vracia len Variabilny sysmbol, tak musim do Variabilneho symbolu vlozit UNIX timestamp 10 miestny
		//override VS
		//milis is new PaymentID and Variabile symbol. 
		long millis = new DateTime().getMillis();
		String payId = Long.toString(millis);
		payId = payId.substring(1);
		payId = payId.substring(0, 10);

		pay.setVs(payId);
		pay.setSs(PaymentFormatUtils.formatSS(payment.getSs()));
		pay.setKs(PaymentFormatUtils.formatKS(payment.getKs()));
		pay.setNote(payment.getRemittanceInformation());
		payments.setPaymentId(payId);
		pay.setUrlRedirect(context.getApplicationCallbackURLForBank() + "?" + PARAM_NAME_OBJ_ID + "=" + payId);

		//payment ready for signing

		//compute sign1

		LOGGER.error("PAYMENT CSOB - payment request to sign ");
		LOGGER.error(pay.createCompleteXML());
		byte[] signedData = CsobUtils.signWithPrivateKeyBC(pay.createCompleteXML().getBytes(Charset.forName("UTF-8")), context.getPublicVODCert().get(), context.getPrivateVODCert().get());

		CsobEnvelope envelope = new CsobEnvelope();
		envelope.setMerchantId(context.getMerchantId());
		envelope.setMessage(Base64.encodeBase64String(signedData));
		LOGGER.error("PAYMENT CSOB - envelope payment ");
		String envelopeXml = envelope.createCompleteXML();
		LOGGER.error(envelopeXml);
		byte[] encryptedEnvelope = CsobUtils.encryptWithPublicKey(envelopeXml.getBytes(Charset.forName("UTF-8")), context.getPublicCSOBCert().get().getPublicKey());

		//make URL
		CsobFormPayRequest payReq = new CsobFormPayRequest();
		String base64String = Base64.encodeBase64String(encryptedEnvelope);
		payReq.zprava("<zprava ofce=\"3111X\">" + base64String + "</zprava>");
		payments.setUrlToCall(makePaymentURL(payReq, context.getCsobGateURL()));

		//it is POST CALL
		payments.setGetCall(false);

		return payments;
	}

	/* (non-Javadoc)O
	 * @see sk.qbsw.dockie.core.payment.paymentProcessor.PaymentProcessor#handleBankPaymentResponse(sk.qbsw.dockie.core.payment.paymentProcessor.BankResponse)
	 */
	@Override
	public PaymentRealization handleBankPaymentResponse (AbstractBankResponse resp)
	{
		CsobBankResponse response = (CsobBankResponse) resp;
		CsobResponseFromVOD responseReturn = new CsobResponseFromVOD();

		try
		{
			//init and parse bank response
			LOGGER.error("PAYMENT CSOB - message to VOD from bank Body raw");
			String bodyBase64 = response.getMessageToVOD().getBody();
			LOGGER.error(bodyBase64);

			String channel = response.getMessageToVOD().getChannel();//channel
			responseReturn.setChannel(channel);//set channel to response object
			LOGGER.error("PAYMENT CSOB - channel " + channel);
			byte[] decryptedMsgXmlByte = CsobUtils.decryptWithPrivateKey(Base64.decodeBase64(bodyBase64), context.getPrivateVODCert().get());
			String decryptedMsgXml;
			decryptedMsgXml = new String(decryptedMsgXmlByte, "UTF-8");
			LOGGER.error("PAYMENT CSOB - decrypted BODY ");
			LOGGER.error(decryptedMsgXml);

			String transactionId;
			transactionId = extractTransactionId(decryptedMsgXml);
			responseReturn.setTransactionId(transactionId);//set tid to response object
			LOGGER.error("PAYMENT CSOB - transactionId " + transactionId);

			//decode and decrypt SOAP  		
			byte[] signedData = extractBodyData(decryptedMsgXml);
			byte[] decodedMessageByte = CsobUtils.extractAndVerifyData(signedData, context.getPublicCSOBCert().get());
			String decodedMessage;
			decodedMessage = new String(decodedMessageByte, "UTF-8");
			LOGGER.error("PAYMENT CSOB - decrypted BODY DATA");
			LOGGER.error(decodedMessage);

			CsobGetCSOBResponce responce = extractXmlData(decodedMessage);
			//get payment 
			String payId = responce.getVs();
			LOGGER.error("PAYMENT CSOB - payment id rec. " + payId);
			if (payId == null)
			{
				throw new IllegalArgumentException("args dont contain Paymennt id in query parameter " + PARAM_NAME_OBJ_ID);
			}
			PaymentRealization payment = getPersistence().getPaymentById(payId);
			if (payment == null)
			{
				LOGGER.error("PAYMENT CSOB - payment not found for payid=" + payId);
				throw new IllegalAccessError("payment not found: args dont contain valid Paymennt id in query parameter " + PARAM_NAME_OBJ_ID);
			}
			LOGGER.error("PAYMENT CSOB - payment for payid=" + payId + " found.");

			//set realization to true
			payment.setPaymentId(payId);
			payment.setBankResponse(decodedMessage);
		
			//construct response to VOD

			//1 is ok 
			//0 is error
			//no error   -  status = 1  msg ""
			//with error -  status = 0  msg "konkretna chyba"
			//this will fill error, and status

			LOGGER.error("PAYMENT CSOB - payment response is " + responce.toString());
			if (responce.getStatus().equals(CsobGetCSOBResponce.STATUS_OK))
			{

				//OK payment
				responseReturn.setStatus("1");
				responseReturn.setErrorMessage("");
				LOGGER.error("PAYMENT CSOB - SUCCESS");
				getActions().handleSuccess(payment);
			}
			else
			{

				responseReturn.setStatus("0");
				LOGGER.error("PAYMENT CSOB - FAIL");
				responseReturn.setErrorMessage("Normal cancel");
				getActions().handleCancel(payment);
			}

			String resultString = responseReturn.toXMLString();
			LOGGER.error("result sent ", resultString);

			byte[] encryptedResultString = CsobUtils.encryptWithPublicKey(resultString.getBytes("UTF-8"), context.getPublicCSOBCert().get().getPublicKey());

			payment.setBankResponse(java.util.Base64.getEncoder().encodeToString(encryptedResultString));
			return payment;
		}
		catch (SAXException | SecurityException e1)
		{
			//security exception when cryptography falied
			LOGGER.error("cryptography falied, there is problem with xml maybe bad signature  ", e1);
			//there is problem with xml maybe bad signature of previous phase 
			//getActions().handleCancel();//handle fraud attempt 
			throw new DecryptionException(e1);

		}
		catch (UnsupportedEncodingException e1)
		{
			//this should not happen .
			//system dont recognize UTF-8
			LOGGER.error("error", e1);
			throw new ConfigurationException(e1);
		}

	}

	/**
	 * @param decodedMessage
	 * @author martinkovic
	 * @version 1.15.0
	 * @param responseReturn 
	 * @return 
	 * @return 
	 * @throws SAXException 
	 * @since 1.15.0 
	 */
	public static CsobGetCSOBResponce extractXmlData (String decodedMessage) throws SAXException
	{
		try
		{
			LOGGER.error("PAYMENT CSOB - processing XML ");
			LOGGER.error(decodedMessage);
			//remove xml header if there is any
			String xml = StringUtils.remove(decodedMessage, "<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			//remove namespace of message
			xml = StringUtils.remove(xml, "xmlns=\"csob/webservices/OFResponce\"");
			LOGGER.error("PAYMENT CSOB - altered XML");
			LOGGER.error(xml);
			StringReader reader = new StringReader(xml);
			InputSource source = new InputSource(reader);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document document = db.parse(source);

			//xpath finder in body 
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			CsobGetCSOBResponce responce = new CsobGetCSOBResponce();

			//need text in xml. 
			//test xpath in http://www.xpathtester.com/xpath
			responce.setStatus(xpath.evaluate("//Status/text()", document));
			responce.setSplatnost(xpath.evaluate("//Splatnost/text()", document));
			responce.setVs(xpath.evaluate("//VS/text()", document));
			responce.setOf(xpath.evaluate("//OF/text()", document));
			responce.setCastka(xpath.evaluate("//Castka/text()", document));
			LOGGER.error("csob responce is " + responce.toString());
			return responce;
		}
		catch (IOException | XPathExpressionException | ParserConfigurationException e)
		{
			//something is wrong with parser initialization. 
			//this is not problem with message
			throw new ConfigurationException(e);
		}

	}

	/**
	 * @param decryptedMsg
	 * @author martinkovic
	 * @version 1.15.0
	 * @throws SAXException 
	 * @since 1.15.0 
	 */
	private String extractTransactionId (String decryptedMsg) throws SAXException
	{
		return readXPath(decryptedMsg, "/messageBody/TransactionID/text()");
	}

	/**
	 * @param decryptedMsg
	 * @author martinkovic
	 * @version 1.15.0
	 * @throws SAXException 
	 * @since 1.15.0 
	 */
	private byte[] extractBodyData (String decryptedMsg) throws SAXException
	{
		return Base64.decodeBase64(readXPath(decryptedMsg, "/messageBody/Data/text()"));
	}

	/**
	 * if exception is thrown then probably there is problem with encryption. 
	 * @param sourceData
	 * @param xpathStr
	 * @return
	 * @throws SAXException
	 * @author martinkovic
	 * @version 1.14.1
	 * @since 1.14.1
	 */
	private String readXPath (String sourceData, String xpathStr) throws SAXException
	{
		try
		{

			String xml = sourceData;

			StringReader reader = new StringReader(xml);
			InputSource source = new InputSource(reader);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document document = db.parse(source);

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			//need text in xml. 
			//test xpath in http://www.xpathtester.com/xpath
			String data = xpath.evaluate(xpathStr, document);

			return data;
		}
		catch (ParserConfigurationException | IOException | XPathExpressionException e)
		{
			throw new ConfigurationException(e);
		}

	}

	private String parsePayID (CsobPayRequest pay)
	{
		//		return pay.getVs() + pay.getSs();
		return pay.getVs();
	}

	/**
	 * @param amount
	 * @return
	 * @author martinkovic
	 * @version 1.15.0
	 * @since 1.15.0 
	 */
	private String normalizeAmountAndConvert (BigDecimal amount)
	{
		return amount.setScale(2, RoundingMode.UP).toPlainString();
	}
}
