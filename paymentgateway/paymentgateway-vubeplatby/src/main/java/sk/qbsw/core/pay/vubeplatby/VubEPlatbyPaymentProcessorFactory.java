package sk.qbsw.core.pay.vubeplatby;

import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentProcessorFactory;

public class VubEPlatbyPaymentProcessorFactory implements PaymentProcessorFactory {

	private VubEPlatbyInitParams context;

	/**
	 * 
	 */
	public VubEPlatbyPaymentProcessorFactory(VubEPlatbyInitParams initparams) {
		this.context = initparams;
	}

	@Override
	public PaymentProcessor createPaymentProcessor() {
		return new VubEPlatbyPaymentProcessor(context);
	}

}
