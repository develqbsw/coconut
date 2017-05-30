package sk.qbsw.core.pay.dummy;

import sk.qbsw.core.pay.base.Payment;
import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentRealization;
import sk.qbsw.core.pay.base.response.AbstractBankResponse;

public class DummyPaymentProcessor extends PaymentProcessor {

	@Override
	public PaymentRealization createPayment(Payment payment) {
		PaymentRealization paymentRealization = new PaymentRealization();
		paymentRealization.setPaymentId(payment.suggestPayId());
		paymentRealization.setGetCall(true);
		paymentRealization.setUrlToCall("\\");// no change
		return paymentRealization;
	}

	@Override
	public PaymentRealization handleBankPaymentResponse(AbstractBankResponse response) {
		DummyResponse dresponse = (DummyResponse) response;
		PaymentRealization realization = getPersistence().getPaymentById(dresponse.getId());

		getActions().handleSuccess(realization);

		realization.setBankResponse("Dummy processed");
		getPersistence().update(realization);

		return realization;
	}

}
