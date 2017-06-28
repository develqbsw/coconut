package sk.qbsw.core.pay.base.payment.request;

import java.math.BigDecimal;

import sk.qbsw.core.pay.base.Currency;

/**
 * Informacie pre jednorazovu platbu. 
 * @author martinkovic
 *
 */
public class OneTimePaymentAmount implements PaymentAmount
{

	BigDecimal amount;

	Currency currency;

	public OneTimePaymentAmount (BigDecimal amount, Currency currency)
	{
		super();
		this.amount = amount;
		this.currency = currency;
	}

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

	@Override
	public BigDecimal totalAmount ()
	{
		return amount;
	}

}
