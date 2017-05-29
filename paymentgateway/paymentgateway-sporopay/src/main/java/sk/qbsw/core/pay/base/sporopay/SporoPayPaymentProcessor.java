/**
 * 
 */
package sk.qbsw.core.pay.base.sporopay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;

import sk.qbsw.core.pay.base.Payment;
import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentRealization;
import sk.qbsw.core.pay.base.response.AbstractBankResponse;
import sk.qbsw.core.pay.base.response.BankResponse;
import sk.qbsw.core.pay.base.sporopay.model.SporoPayRequest;
import sk.qbsw.core.pay.base.sporopay.model.SporoPayResponse;
import sk.qbsw.core.pay.base.util.PaymentFormatUtils;

/**
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class SporoPayPaymentProcessor extends PaymentProcessor
{

	private SporoPayInitParams context;

	/**
	 * @param context
	 */
	public SporoPayPaymentProcessor (SporoPayInitParams context)
	{
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.dockie.core.payment.paymentProcessor.PaymentProcessor#createPayment(sk.qbsw.dockie.core.payment.paymentProcessor.Payment)
	 */
	@Override
	public PaymentRealization createPayment (Payment payment)
	{
		PaymentRealization payments = new PaymentRealization();
		SporoPayRequest pay = new SporoPayRequest();
		pay.cislo_uctu(context.getMerchantAccountNumber());
		pay.predcislo_uctu(context.getMerchantAccountPrefix());
		pay.kbanky(context.getMerchantBankNumber());
		pay.suma(normalizeAmountAndConvert(payment.getAmount()));
		pay.url(context.getApplicationCallbackURLForBank());
		pay.vs(PaymentFormatUtils.formatVS(payment.getVs()));
		pay.ss(PaymentFormatUtils.formatSS(payment.getSs()));
		pay.mena("EUR");
		payments.setPaymentId(parsePayID(pay));
		pay.param(payments.getPaymentId());
		//compute sign1
		String sign1 = pay.computeSign1(context.getPasswordDelegate().get());
		pay.sign(sign1);

		//make URL
		payments.setUrlToCall(makePaymentURL(pay, context.getSporopayGateURL()));

		//it is GET CALL
		payments.setGetCall(true);

		return payments;
	}

	/* (non-Javadoc)O
	 * @see sk.qbsw.dockie.core.payment.paymentProcessor.PaymentProcessor#handleBankPaymentResponse(sk.qbsw.dockie.core.payment.paymentProcessor.BankResponse)
	 */
	@Override
	public PaymentRealization handleBankPaymentResponse (AbstractBankResponse resp)
	{
		SporoPayResponse response = new SporoPayResponse(((BankResponse)resp).getParams());

		if (response.wasError())
		{
			//nothing to found 
			//throw error
			getActions().handleCancel();
			throw new IllegalArgumentException("call dont represent any adresable payment");
		}

		PaymentRealization payment = getPersistence().getPaymentById(parsePayID(response));
		//protection for DOS attacks

		if (payment == null)
		{
			throw new InvalidParameterException("payment not found");
		}
		//payment is not null 


		//before start, save bank response
		payment.setBankResponse(makePaymentURL(response, "\\"));

		//overienie hmac a ecsda

		String sign2 = response.computeSign2(context.getPasswordDelegate().get());
		if (!sign2.equals(response.sign2()))
		{
			getActions().handleFraud(payment);
		}

		if ("OK".equals(response.result()))
		{
			if ("OK".equals(response.realization()))
			{
				//OK. 
				getActions().handleSuccess(payment);
			}
			else
			{
				//cant say  check emails form bank
				getActions().handleUncertain(payment);
			}
		}
		else
		{
			getActions().handleCancel(payment);
		}

		getPersistence().update(payment);
		return payment;
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
	
	/**
	 * generuje paymentID z VS a SS
	 * @param pay
	 * @return
	 * @author martinkovic
	 * @version 1.15.0
	 * @since 1.15.0
	 */
	protected String parsePayID (SporoPayRequest pay)
	{
		return pay.vs() + pay.ss();
	}

}
