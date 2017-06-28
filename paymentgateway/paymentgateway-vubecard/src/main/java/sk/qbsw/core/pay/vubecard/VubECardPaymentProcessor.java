package sk.qbsw.core.pay.vubecard;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentRealization;
import sk.qbsw.core.pay.base.PaymentRequest;
import sk.qbsw.core.pay.base.exception.DecryptionException;
import sk.qbsw.core.pay.base.exception.InvalidRequestException;
import sk.qbsw.core.pay.base.reciept.PaymentReciept;
import sk.qbsw.core.pay.base.reciept.PaymentRecieptImpl;
import sk.qbsw.core.pay.base.response.AbstractBankResponse;
import sk.qbsw.core.pay.base.response.BankResponse;
import sk.qbsw.core.pay.vubecard.model.CVubCardRequest;
import sk.qbsw.core.pay.vubecard.model.CVubCardResponse;
import sk.qbsw.core.pay.vubecard.model.CVubGeneralCall;

public class VubECardPaymentProcessor extends PaymentProcessor
{

	private VubECardInitParams context;

	public VubECardPaymentProcessor (VubECardInitParams context)
	{
		this.context = context;
	}

	private String makeSalt ()
	{
		return RandomStringUtils.randomAlphanumeric(20);
	}


	@Override
	public PaymentReciept createPayment (PaymentRequest payment)
	{
		CVubCardRequest pay = new CVubCardRequest();

		pay.clientid(context.getMerchantId());

		pay.amount(payment.getAmount().totalAmount().toPlainString());

		pay.okUrl(context.getCallbackOkUrl() + "?op=confirm");
		pay.failUrl(context.getCallbackFailUrl() + "?op=deny");

		String payId2 = payment.getIdentification().getPaymentId();
		pay.rnd(makeSalt());
		pay.oid(payId2);

		String hashString = pay.generateHashString(Base64.encodeBase64URLSafeString(context.getPasswordDelegate().get()));
		String hash = pay.computeHash(hashString);
		pay.messageHash(hash);

		//TODO make recurring payments

		//		if (priceSummary.isRecurring())
		//		{
		//			payments.setMode(EPaymentMode.RECURRING);
		//			pay.RecurringFrequency("1");
		//			pay.RecurringFrequencyUnit("M");
		//			pay.RecurringPaymentNumber(priceSummary.getRecurringCount().toString());
		//		}

		//make URL

		String url = makePaymentURL(pay, context.getVubeplatbyGateURL());


		//pl. brana podporuje reccurence , ale nemame zatial implementovane 

		return new PaymentRecieptImpl(payId2, url, true);

	}

	@Override
	public PaymentRealization handleBankPaymentResponse (AbstractBankResponse resp)
	{

		CVubCardResponse response = new CVubCardResponse( ((BankResponse) resp).getParams());

		//nacitanie platby z DB
		PaymentRealization payment = getPersistence().getPaymentById(response.oid());

		//overit integritu
		if (response.hashAlgorithm().equalsIgnoreCase("ver2"))
		{
			//response.computeShaHash(key)
			String secretString = Base64.encodeBase64URLSafeString(context.getPasswordDelegate().get());
			String hashval = response.generateHashParamsString(secretString);
			String hash = response.computeHash(hashval);

			String hashparamsval = response.HASHPARAMSVAL() + CVubGeneralCall.FIELD_DELIMITER + secretString;

			if (hashval.equals(hashparamsval) && response.HASH().equals(hash))
			{
				//OK message is verified. 
			}
			else
			{
				//bad...
				getActions().handleFraud(payment);
				throw new DecryptionException("nesedi overenie platby");
			}

		}
		else
		{
			throw new InvalidRequestException("Unsupported hash algorithm " + response.hashAlgorithm());
		}

		//overena platba 


		String mdStatus = response.mdStatus();
		if (mdStatus.equals("1") || mdStatus.equals("2") || mdStatus.equals("3") || mdStatus.equals("4"))
		{
			//success
			getActions().handleSuccess(payment);
		}
		else
		{
			getActions().handleCancel(payment);
		}

		if (response.Response().equalsIgnoreCase("Approved"))
		{
			//approved payment
			//success
			getActions().handleSuccess(payment);
		}
		else
		{
			//deceliend
			//			ErrMsg
			getActions().handleCancel(payment);

		}
		String gateResp = makePaymentURL(response, "\\");
		payment.setBankResponse(gateResp);
		getPersistence().update(payment);

		return payment;


	}

}
