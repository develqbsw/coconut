package sk.qbsw.dockie.core.gopay.model;

import java.util.List;

/**
 * The Class CGopaySimplePaymentResponse.
 *
 * @author martinkovic
 * @version 1.7.0
 * @since  1.7.0
 */
public class CGopaySimplePaymentResponse
{
	private Long id;
	private String order_number;
	private String state;
	private Long amount;
	private String currency;
	private CGopayPayer payer;
	private CGopayTarget target;
	private List<CGopayAdditionalParameter> additional_params;
	private String lang;
	private String gw_url;

	public Long getId ()
	{
		return id;
	}

	public void setId (Long id)
	{
		this.id = id;
	}

	public String getOrder_number ()
	{
		return order_number;
	}

	public void setOrder_number (String order_number)
	{
		this.order_number = order_number;
	}

	public String getState ()
	{
		return state;
	}

	public void setState (String state)
	{
		this.state = state;
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

	public String getGw_url ()
	{
		return gw_url;
	}

	public void setGw_url (String gw_url)
	{
		this.gw_url = gw_url;
	}

}
