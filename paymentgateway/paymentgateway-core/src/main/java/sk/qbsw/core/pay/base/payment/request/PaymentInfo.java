/**
 * 
 */
package sk.qbsw.core.pay.base.payment.request;

import java.math.BigDecimal;
import java.util.List;

import sk.qbsw.core.pay.base.Currency;

/** 
 * entita drži dodatočné informácie o platbe, ktoré niesu povinné na priebeh a identifikaciu platby
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public interface PaymentInfo
{
	/**
	 * Note for referee
	 * @return
	 */
	String getNote();
	
	
}
