package sk.qbsw.core.pay.tatrapay;

import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentProcessorFactory;

public class TatrapayPaymentProcessorFactory implements PaymentProcessorFactory
{

	private TatrapayInitParams context;

	/**
	 * 
	 */
	public TatrapayPaymentProcessorFactory (TatrapayInitParams initparams)
	{
		this.context = initparams;
	}

	@Override
	public PaymentProcessor createPaymentProcessor ()
	{
		return new TatrapayPaymentProcessor(context);
	}

}
