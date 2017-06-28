package sk.qbsw.core.pay.dummy;

import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentRealization;
import sk.qbsw.core.pay.base.PaymentRequest;
import sk.qbsw.core.pay.base.reciept.PaymentReciept;
import sk.qbsw.core.pay.base.reciept.PaymentRecieptImpl;
import sk.qbsw.core.pay.base.response.AbstractBankResponse;

public class DummyPaymentProcessor extends PaymentProcessor {

	@Override
	public PaymentReciept createPayment(PaymentRequest payment) {
		PaymentReciept paymentRealization = new PaymentRecieptImpl(payment.getIdentification().getPaymentId(),"\\",true);
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
