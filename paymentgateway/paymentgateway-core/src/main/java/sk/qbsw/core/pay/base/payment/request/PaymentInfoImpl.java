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
public class PaymentInfoImpl implements PaymentInfo
{
	/**
	 * Note for referee
	 * @return
	 */
	private String note;

	public PaymentInfoImpl (String note)
	{
		this.note = note;
	}

	public String getNote ()
	{
		return note;
	}

	public void setNote (String note)
	{
		this.note = note;
	}


}
