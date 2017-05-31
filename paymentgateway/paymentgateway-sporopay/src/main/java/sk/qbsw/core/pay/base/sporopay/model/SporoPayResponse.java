package sk.qbsw.core.pay.base.sporopay.model;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class SporoPayResponse.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public class SporoPayResponse extends SporoPayRequest
{
	private static final String SIGN2 = "sign2";
	private static final String SIGN3 = "sign3";
	private static final String RESULT = "result";
	private static final String REAL = "real";

	public SporoPayResponse (Map<String, String> map)
	{
		super(map);
	}

	public String computeSign2 (byte[] bs)
	{
		return computeHash(bs, getSign2String());
	}

	public String computeSign3 (byte[] bs)
	{
		return computeHash(bs, getSign3String());
	}

	public String getSign2String ()
	{
		String sign = "";
		sign += getWithSemicolumn(PREDCISLO_UCTU);
		sign += getWithSemicolumn(CISLO_UCTU);
		sign += getWithSemicolumn(KBANKY);
		sign += getWithSemicolumn(SUMA);
		sign += getWithSemicolumn(MENA);
		sign += getWithSemicolumn(VS);
		sign += getWithSemicolumn(SS);
		sign += getWithSemicolumn(URL);
		sign += getWithSemicolumn(PARAM);
		sign += getWithSemicolumn(RESULT);
		sign += getWithSemicolumn(REAL);

		//remove last ;
		sign = StringUtils.chomp(sign);
		sign = StringUtils.removeEnd(sign, ";");
		return sign;

	}

	//sign3 sa pouziva pre emaily
	public String getSign3String ()
	{
		String sign = "";
		sign += getWithSemicolumn(PREDCISLO_UCTU);
		sign += getWithSemicolumn(CISLO_UCTU);
		sign += getWithSemicolumn(KBANKY);
		sign += getWithSemicolumn(SUMA);
		sign += getWithSemicolumn(MENA);
		sign += getWithSemicolumn(VS);
		sign += getWithSemicolumn(SS);
		//		sign += getWithSemicolumn(URL);
		//		sign += getWithSemicolumn(PARAM);
		sign += getWithSemicolumn(RESULT);
		sign += getWithSemicolumn(REAL);
		//		sign += getWithSemicolumn(SIGN2);//PODLA DOKUMENTACIE TU MA BYT SIGN3  asi tam maju chybu.  

		//remove last ;
		sign = StringUtils.chomp(sign);
		sign = StringUtils.removeEnd(sign, ";");
		return sign;

	}

	public SporoPayResponse sign2 (String val)
	{
		set(SIGN2, val);
		return this;
	}

	public SporoPayResponse sign3 (String val)
	{
		set(SIGN3, val);
		return this;
	}

	public SporoPayResponse result (String val)
	{
		set(RESULT, val);
		return this;
	}

	public SporoPayResponse realization (String val)
	{
		set(REAL, val);
		return this;
	}

	public String sign2 ()
	{
		return get(SIGN2);
	}

	public String sign3 ()
	{
		return get(SIGN3);
	}

	public String result ()
	{
		return get(RESULT);
	}

	public String realization ()
	{
		return get(REAL);
	}

	public boolean wasError ()
	{
		if ("OK".equalsIgnoreCase(realization()) && "OK".equalsIgnoreCase(result()))
		{
			return false;
		}

		return true;
	}

}
