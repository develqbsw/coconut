/**
 * 
 */
package sk.qbsw.core.pay.base.payment.request;

import java.math.BigDecimal;

import sk.qbsw.core.pay.base.Currency;

/** 

 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public interface PaymentAmount
{
	/**
	 * method returns total amount of funds, promised by this contract. 
	 * @return
	 */
	BigDecimal totalAmount();

	/**
	 * currency 
	 * @return
	 */
	Currency getCurrency();


	
}
