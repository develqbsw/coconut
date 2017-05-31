package sk.qbsw.core.pay.base.sporopay.model;

import java.util.Map;

import sk.qbsw.core.pay.base.response.BankResponse;

/**
 * The Class CSlspGeneralCall.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public abstract class SporoPayGeneralCall extends BankResponse
{

	public SporoPayGeneralCall (Map<String, String> params)
	{
		super(params);

	}

	public String getWithSemicolumn (String param)
	{
		return getWithPostfix(param, ";", true);
	}

}
