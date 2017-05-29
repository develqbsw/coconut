package sk.qbsw.core.pay.base.csob.model;

import java.util.HashMap;
import java.util.Map;

import sk.qbsw.core.pay.base.response.BankResponse;

/**
 * The Class SporoPayRequest.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public class CsobFormPayRequest extends BankResponse
{
	protected static final String ZPRAVA = "ZPRAVA";

	/**
	 * @param params
	 */
	public CsobFormPayRequest ()
	{
		super(new HashMap<String, String>());
	}

	/**
	 * @param params
	 */
	public CsobFormPayRequest (Map<String, String> params)
	{
		super(params);
	}

	public CsobFormPayRequest zprava (String val)
	{
		set(ZPRAVA, val);
		return this;
	}

}
