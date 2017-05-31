package sk.qbsw.core.pay.vubeplatby.model;

import java.util.Map;

import sk.qbsw.core.pay.base.response.BankResponse;

/**
 * The Class CVubGeneralCall.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public abstract class CVubGeneralCall extends BankResponse
{

	public static final String FIELD_DELIMITER = "|";

	public CVubGeneralCall (Map<String, String> params)
	{
		super(params);

	}

	public String getWithVbar (String param)
	{
		return getWithPostfix(param, FIELD_DELIMITER, false);
	}

}
