package sk.qbsw.core.pay.tatrapay;

import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentRealization;
import sk.qbsw.core.pay.base.PaymentRequest;
import sk.qbsw.core.pay.base.exception.ConfigurationException;
import sk.qbsw.core.pay.base.exception.DecryptionException;
import sk.qbsw.core.pay.base.exception.InvalidRequestException;
import sk.qbsw.core.pay.base.reciept.PaymentReciept;
import sk.qbsw.core.pay.base.reciept.PaymentRecieptImpl;
import sk.qbsw.core.pay.base.response.AbstractBankResponse;
import sk.qbsw.core.pay.base.response.BankResponse;
import sk.qbsw.core.pay.tatrapay.model.CTBPayRequest;
import sk.qbsw.core.pay.tatrapay.model.CTBPayResponse;

public class TatrapayPaymentProcessor extends PaymentProcessor
{

	private static final AbstractBankResponse BankResponse = null;
	private TatrapayInitParams context;

	public TatrapayPaymentProcessor (TatrapayInitParams context)
	{
		this.context = context;
	}

	@Override
	public PaymentReciept createPayment (PaymentRequest payment)
	{
		failOnRecurring(payment);
		
		CTBPayRequest pay = new CTBPayRequest();
		pay.currency().guiLang("sk");
		pay.redirectTimeout(false);
		pay.timestamp(new DateTime());
		pay.redirectURL(context.getApplicationCallbackURLForBank());
		pay.reminderEmail(context.getNotifyEmail());
		pay.merchantId(context.getMerchantId());
		pay.reference(payment.getIdentification().getPaymentId());
		pay.amount(payment.getAmount().totalAmount());
		// compute hmac
		String hmac = pay.computeHMAC(context.getPasswordDelegate().get());
		pay.hmac(hmac);

		// make URL
		String payId = pay.vs() + pay.ss();
		String makePaymentURL = makePaymentURL(pay, context.getTatrapayGateURL());

		// is get.
		PaymentRecieptImpl realization = new PaymentRecieptImpl(payId, makePaymentURL, true);

		return realization;
	}

	

	@Override
	public PaymentRealization handleBankPaymentResponse (AbstractBankResponse resp)
	{
		BankResponse bResp = (BankResponse) resp;

		CTBPayResponse response = new CTBPayResponse(bResp.getParams());
		PaymentRealization realization = getPersistence().getPaymentById(getPayId(response));
		// protection for DOS attacks
		if (realization == null)
		{
			throw new InvalidRequestException("invalid request came");
		}

		// overienie hmac a ecsda
		String computedHmac = response.computeHMAC(context.getPasswordDelegate().get());
		if (!computedHmac.equals(response.hmac()))
		{
			getActions().handleFraud(realization);
			throw new DecryptionException("invalid signature. Most likely fraud");
		}
		String key = getEcdsaKey(Integer.getInteger(response.ecdsaKey()));
		if (!response.verifyECDSA(key))
		{
			getActions().handleFraud(realization);
			throw new DecryptionException("invalid signature ECDSA. Most likely fraud");
		} ;

		if ("OK".equals(response.resolution()))
		{
			// OK.
			getActions().handleSuccess(realization);
		}
		else if ("TOUT".equals(response.resolution()))
		{
			getActions().handleUncertain(realization);
		}
		else
		{
			// FAIL
			getActions().handleCancel(realization);
		}

		String gateResp = makePaymentURL(response, "\\");
		realization.setBankResponse(gateResp);
		getPersistence().update(realization);
		return realization;
	}

	public String getEcdsaKey (int i)
	{
		ResponseEntity<String> entity = context.getHttpClient().getForEntity(context.getTatrapayCertsUrl(), String.class);
		if (entity.getStatusCode() != HttpStatus.OK)
		{
			throw new ConfigurationException("Bad connection to " + context.getTatrapayCertsUrl() + " status " + entity.getStatusCode());
		}

		return extractCert(i, entity.getBody());
	}

	private static String extractCert (int i, String entity)
	{
		String[] split = entity.split("KEY_ID: ");
		for (String rawBody : split)
		{
			if (rawBody.startsWith(Integer.toString(i)))
			{
				String[] parts = rawBody.split("-----");
				if (parts.length > 2)
				{
					return parts[2].replaceAll("\n", "");
				}

			}
		}
		return null;
	}

	private String getPayId (CTBPayRequest pay)
	{
		return pay.vs() + pay.ss();
	}

}
