package sk.qbsw.core.pay.vubeplatby.model;

import java.util.Map;

/**
 * The Class CVubPayResponse.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public class CVubPayResponse extends CVubPayRequest
{

	protected static final String RES = "RES";

	public CVubPayResponse (Map<String, String> params)
	{
		super(params);
	}

	public String getSignRetString ()
	{
		String sign = "";
		sign += get(VS);
		sign += get(SS);
		sign += get(RES);

		return sign;
	}

	/**
	 *výsledok spracovania transakcie na strane banky. tento parameter môže maĢ 2 hodnoty:
	- OK – platba bola zrealizovaná
	- FAIL – pri realizácii sa vyskytla chyba
	 * @return
	 */
	public String resolution ()
	{
		return get(RES);
	}

}
