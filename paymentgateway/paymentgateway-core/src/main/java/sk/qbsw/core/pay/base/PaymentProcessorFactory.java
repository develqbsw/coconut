/**
 * 
 */
package sk.qbsw.core.pay.base;

import org.springframework.web.client.RestTemplate;

/**
 * payment processor factory interface for creation of paymentprocessors
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public interface PaymentProcessorFactory
{
	/**
	 * crreates payment processor for this factory. in production mode, and default RestTemplate client. 
	 * @return
	 */
	public PaymentProcessor createPaymentProcessor ();
	
	/**
	 * crreates payment processor for this factory. 
	 * @param prodModeEnabled if true then production mode will be enabled in gateway
	 * @param apiclient client to call banks, if nescessary. not all banks requre it, but is good practise to provide it. 
	 * @return
	 */
	public PaymentProcessor createPaymentProcessor (boolean prodModeEnabled, RestTemplate apiclient);

}
