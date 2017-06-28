/**
 * 
 */
package sk.qbsw.core.pay.base.payment.request;

import java.math.BigDecimal;

/** 
 * @author martinkovic
 * @version 1.18.0
 * @since 1.18.0 
 *
 */
public interface PaymentIdentification
{	
	/**
	 * primary form of payment identification.
	 * @return
	 */
	String getPaymentId();
	
	

}
