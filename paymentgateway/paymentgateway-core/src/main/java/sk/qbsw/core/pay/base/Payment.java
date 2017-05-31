/**
 * 
 */
package sk.qbsw.core.pay.base;

import java.math.BigDecimal;

/** 
 * base interface for payment information, witch are used to realize payment 
 * toto sluzi ako 
 *  -predpis pri vytvarani platby
 *  -reprezentacia platby v systeme. 
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public interface Payment
{
	Long getVs ();

	Long getSs ();

	Long getKs ();

	String getRemittanceInformation ();

	BigDecimal getAmount ();

	Currency getCurrency ();

	PaymentRealization getRealization ();

	String suggestPayId();
}
