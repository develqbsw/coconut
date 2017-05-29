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
public class PaymentImpl implements Payment
{
	private Long vs;

	private Long ss;

	private Long ks;

	private String remittanceInformation;

	private BigDecimal amount;

	private Currency currency;

	PaymentRealization realization;

	/**
	 * @return the vs
	 */
	public Long getVs ()
	{
		return vs;
	}

	/**
	 * @param vs the vs to set
	 */
	public void setVs (Long vs)
	{
		this.vs = vs;
	}

	/**
	 * @return the ss
	 */
	public Long getSs ()
	{
		return ss;
	}

	/**
	 * @param ss the ss to set
	 */
	public void setSs (Long ss)
	{
		this.ss = ss;
	}

	

	/**
	 * @return the remittanceInformation
	 */
	public String getRemittanceInformation ()
	{
		return remittanceInformation;
	}

	/**
	 * @param remittanceInformation the remittanceInformation to set
	 */
	public void setRemittanceInformation (String referal)
	{
		this.remittanceInformation = referal;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount ()
	{
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount (BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * @return the currency
	 */
	public Currency getCurrency ()
	{
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency (Currency currency)
	{
		this.currency = currency;
	}

	/**
	 * @return the realization
	 */
	public PaymentRealization getRealization ()
	{
		return realization;
	}

	/**
	 * @param realization the realization to set
	 */
	public void setRealization (PaymentRealization realization)
	{
		this.realization = realization;
	}

	public Long getKs ()
	{
		return ks;
	}

	public void setKs (Long ks)
	{
		this.ks = ks;
	}

}
