/**
 * 
 */
package sk.qbsw.core.pay.base.csob;

import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentProcessorFactory;

/**
 * impl. payment procesorov
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class CsobPaymentProcessorFactoryImpl implements PaymentProcessorFactory
{
	private CsobInitParams context;

	/**
	 * 
	 */
	public CsobPaymentProcessorFactoryImpl (CsobInitParams initparams)
	{
		this.context = initparams;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.dockie.core.payment.paymentProcessor.PaymentProcessorFactory#createPaymentProcessor()
	 */
	@Override
	public PaymentProcessor createPaymentProcessor ()
	{
		return new CsobPaymentProcessor(context);
	}

}
