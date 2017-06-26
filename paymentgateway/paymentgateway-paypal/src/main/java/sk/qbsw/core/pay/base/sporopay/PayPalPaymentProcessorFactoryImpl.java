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
public class PayPalPaymentProcessorFactoryImpl implements PaymentProcessorFactory
{
	private PayPalInitParams context;

	/**
	 * 
	 */
	public PayPalPaymentProcessorFactoryImpl (PayPalInitParams initparams)
	{
		this.context = initparams;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.dockie.core.payment.paymentProcessor.PaymentProcessorFactory#createPaymentProcessor()
	 */
	public PaymentProcessor createPaymentProcessor ()
	{
		return new PayPalPaymentProcessor(context);
	}

}
