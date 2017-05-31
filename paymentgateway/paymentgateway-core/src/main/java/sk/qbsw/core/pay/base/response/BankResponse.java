/**
 * 
 */
package sk.qbsw.core.pay.base.response;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Object wraps bank responses to our payment
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class BankResponse extends AbstractBankResponse
{
	private Map<String, String> params = new HashMap<String, String>();

	public BankResponse (Map<String, String> params)
	{
		super();
		this.params = params;
	}

	public String get (String param)
	{
		return StringUtils.defaultString(params.get(param), "");
	}

	public String getWithPostfix (String param, String postfix, boolean skipOnBlank)
	{
		String val = get(param);
		if (!StringUtils.isBlank(val))
		{
			return val + postfix;
		}
		if (!skipOnBlank)
		{
			val += postfix;
		}
		return val;
	}

	protected String set (String param, String val)
	{
		return params.put(param, val);

	}

	public Map<String, String> getParams ()
	{
		return params;
	}

}
