package sk.qbsw.core.pay.base.payment.request;

import java.math.BigDecimal;

import sk.qbsw.core.pay.base.Currency;

/**
 * Informacie pre jednorazovu platbu. 
 * @author martinkovic
 *
 */
public class ReccuringPaymentAmount implements PaymentAmount
{
	/**
	 * Sum to pay for required period
	 */
	BigDecimal amount;

	/**
	 * currency of this payment
	 */
	Currency currency;

	/**
	 * period in witch will be payment executed
	 */
	ReccuringPeriod period;

	/**
	 * every x period will be payment executed. 
	 * say if i want every 3rd month to get payment. 
	 * this will have number 3
	 */
	int recurrencePeriod;

	/**
	 * number of total payments executed by full duration of this payment. 
	 */
	int reccuringCount;

	public BigDecimal getAmount ()
	{
		return amount;
	}

	public void setAmount (BigDecimal amount)
	{
		this.amount = amount;
	}

	public Currency getCurrency ()
	{
		return currency;
	}

	public void setCurrency (Currency currency)
	{
		this.currency = currency;
	}

	public ReccuringPeriod getPeriod ()
	{
		return period;
	}

	public void setPeriod (ReccuringPeriod period)
	{
		this.period = period;
	}

	public int getRecurrencePeriod ()
	{
		return recurrencePeriod;
	}

	public void setRecurrencePeriod (int recurrencePeriod)
	{
		this.recurrencePeriod = recurrencePeriod;
	}

	public int getReccuringCount ()
	{
		return reccuringCount;
	}

	public void setReccuringCount (int reccuringCount)
	{
		this.reccuringCount = reccuringCount;
	}

	@Override
	public BigDecimal totalAmount ()
	{
		return amount.multiply(BigDecimal.valueOf(reccuringCount));
	}

}
