/**
 * 
 */
package sk.qbsw.core.pay.base.sporopay;

import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentProcessorFactory;

/**
 * impl. payment procesorov
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class GoPayPaymentProcessorFactoryImpl implements PaymentProcessorFactory
{
	private GoPayInitParams context;

	/**
	 * 
	 */
	public GoPayPaymentProcessorFactoryImpl (GoPayInitParams initparams)
	{
		this.context = initparams;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.dockie.core.payment.paymentProcessor.PaymentProcessorFactory#createPaymentProcessor()
	 */
	public PaymentProcessor createPaymentProcessor ()
	{
		return new GoPayPaymentProcessor(context);
	}

}
