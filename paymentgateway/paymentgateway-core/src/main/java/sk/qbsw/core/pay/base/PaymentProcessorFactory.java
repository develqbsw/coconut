/**
 * 
 */
package sk.qbsw.core.pay.base;

/**
 * payment processor factory interface for creation of paymentprocessors
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public interface PaymentProcessorFactory
{
	public PaymentProcessor createPaymentProcessor();

}
