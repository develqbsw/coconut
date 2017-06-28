package sk.qbsw.core.pay.vubeplatby;

import sk.qbsw.core.pay.base.PaymentRequest;
import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentRealization;
import sk.qbsw.core.pay.base.exception.DecryptionException;
import sk.qbsw.core.pay.base.exception.InvalidRequestException;
import sk.qbsw.core.pay.base.payment.request.OneTimePaymentAmount;
import sk.qbsw.core.pay.base.payment.request.PaymentAmount;
import sk.qbsw.core.pay.base.payment.request.PaymentIdentification;
import sk.qbsw.core.pay.base.payment.request.SlovakPaymentIdentification;
import sk.qbsw.core.pay.base.response.AbstractBankResponse;
import sk.qbsw.core.pay.base.response.BankResponse;
import sk.qbsw.core.pay.base.util.PaymentFormatUtils;
import sk.qbsw.core.pay.vubeplatby.model.CVubPayRequest;
import sk.qbsw.core.pay.vubeplatby.model.CVubPayResponse;

public class VubEPlatbyPaymentProcessor extends PaymentProcessor
{

	private VubEPlatbyInitParams context;

	public VubEPlatbyPaymentProcessor (VubEPlatbyInitParams context)
	{
		this.context = context;
	}

	@Override
	public PaymentRealization createPayment (PaymentRequest payment)
	{
		failOnRecurring(payment);
		SlovakPaymentIdentification identification = getSlovakInfo(payment);
		
		CVubPayRequest pay = new CVubPayRequest();
		
		pay.merchantId(context.getMerchantId());
		pay.amount(payment.getAmount().totalAmount());
		pay.vs(identification.getVs());
		pay.ss(identification.getSs());
		pay.ks(identification.getKs());
		pay.description(payment.getInfo().getNote());

		pay.redirectURL(context.getApplicationCallbackURLForBank());
		pay.reminderEmail(context.getNotifyEmail());
		String payId = pay.vs() + pay.ss();

		String sign1 = pay.computeSign(context.getPasswordDelegate().get());
		pay.sign(sign1);


		//make URL
		String url = makePaymentURL(pay, context.getVubeplatbyGateURL());


		//pl. brana podporuje reccurence , ale nemame zatial implementovane 

		return PaymentRealization.of(payId, url, true);
	}

	@Override
	public PaymentRealization handleBankPaymentResponse (AbstractBankResponse resp)
	{
		CVubPayResponse response = new CVubPayResponse(((BankResponse)resp).getParams());
		PaymentRealization realization = getPersistence().getPaymentById(getPayId(response));
		//protection for DOS attacks
		if (realization == null)
		{
			throw new InvalidRequestException("invalid request has come");
		}

		//overienie hmac a ecsda
		String computeSign = response.computeSign(context.getPasswordDelegate().get(), response.getSignRetString());
		if (!computeSign.equalsIgnoreCase(response.sign()))
		{
			getActions().handleFraud(realization);
			throw new DecryptionException("invalid signature. Most likely fraud");
		}

		//if verified

		if ("OK".equals(response.resolution()))
		{

			//OK. 
			getActions().handleSuccess(realization);
		}
		else
		{
			getActions().handleCancel(realization);

		}
		String gateResp = makePaymentURL(response, "\\");
		realization.setBankResponse(gateResp);
		getPersistence().update(realization);
		return realization;
	}


	private String getPayId (CVubPayResponse pay)
	{
		return pay.vs() + pay.ss();
	}
}
