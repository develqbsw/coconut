package sk.qbsw.dockie.core.gopay.model;

import java.util.List;

/**
 * The Class CGopaySimplePaymentRequest.
 *
 * @author martinkovic
 * @version 1.7.0
 * @since  1.7.0
 */
public class CGopaySimplePaymentRequest
{
	private CGopayPayer payer;
	private CGopayTarget target;
	private Long amount;
	/**
	 * 
	CZK 	České koruny
	EUR 	Eura
	PLN 	Polský złoty
	HUF 	Maďarský forint
	GBP 	Britská libra
	USD 	Americký dolar
	 */
	private String currency;
	private String order_number;
	private String order_description;
	private List<CGopayItem> items;
	private CGopayCallback callback;
	private CGopayRecurrence recurrence;
	private List<CGopayAdditionalParameter> additional_params;
	private String lang;

	public CGopayPayer getPayer ()
	{
		return payer;
	}

	public void setPayer (CGopayPayer payer)
	{
		this.payer = payer;
	}

	public CGopayTarget getTarget ()
	{
		return target;
	}

	public void setTarget (CGopayTarget target)
	{
		this.target = target;
	}

	public Long getAmount ()
	{
		return amount;
	}

	public void setAmount (Long amount)
	{
		this.amount = amount;
	}

	public String getCurrency ()
	{
		return currency;
	}

	public void setCurrency (String currency)
	{
		this.currency = currency;
	}

	public String getOrder_number ()
	{
		return order_number;
	}

	public void setOrder_number (String order_number)
	{
		this.order_number = order_number;
	}

	public String getOrder_description ()
	{
		return order_description;
	}

	public void setOrder_description (String order_description)
	{
		this.order_description = order_description;
	}

	public List<CGopayItem> getItems ()
	{
		return items;
	}

	public void setItems (List<CGopayItem> items)
	{
		this.items = items;
	}

	public CGopayCallback getCallback ()
	{
		return callback;
	}

	public void setCallback (CGopayCallback callback)
	{
		this.callback = callback;
	}

	public List<CGopayAdditionalParameter> getAdditional_params ()
	{
		return additional_params;
	}

	public void setAdditional_params (List<CGopayAdditionalParameter> additional_params)
	{
		this.additional_params = additional_params;
	}

	public String getLang ()
	{
		return lang;
	}

	public void setLang (String lang)
	{
		this.lang = lang;
	}

	public CGopayRecurrence getRecurrence ()
	{
		return recurrence;
	}

	public void setRecurrence (CGopayRecurrence recurrence)
	{
		this.recurrence = recurrence;
	}

}
