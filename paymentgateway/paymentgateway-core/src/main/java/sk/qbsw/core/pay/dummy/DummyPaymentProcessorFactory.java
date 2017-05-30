package sk.qbsw.core.pay.dummy;

import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentProcessorFactory;

public class DummyPaymentProcessorFactory implements PaymentProcessorFactory {

	private DummyInitParams context;

	/**
	 * 
	 */
	public DummyPaymentProcessorFactory(DummyInitParams initparams) {
		this.context = initparams;
	}

	@Override
	public PaymentProcessor createPaymentProcessor() {
		return new DummyPaymentProcessor();
	}

}
