package sk.qbsw.core.pay.vubecard;

import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentProcessorFactory;

public class VubECardPaymentProcessorFactory implements PaymentProcessorFactory {

	private VubECardInitParams context;

	/**
	 * 
	 */
	public VubECardPaymentProcessorFactory(VubECardInitParams initparams) {
		this.context = initparams;
	}

	@Override
	public PaymentProcessor createPaymentProcessor() {
		return new VubECardPaymentProcessor(context);
	}

}
