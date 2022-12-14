package sk.qbsw.core.pay.vubeplatby.model;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * The Class CVubPayRequest.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public class CVubPayRequest extends CVubGeneralCall
{
	protected static final String RURL = "RURL";
	protected static final String MID = "MID";
	protected static final String KS = "CS";
	protected static final String SS = "SS";
	protected static final String VS = "VS";
	protected static final String AMT = "AMT";
	protected static final String REM = "REM";
	protected static final String DESC = "DESC";
	protected static final String RSMS = "RSMS";
	protected static final String SIGN = "SIGN";

	public CVubPayRequest ()
	{
		super(new HashMap<String, String>());
	}

	protected CVubPayRequest (Map<String, String> map)
	{
		super(map);
	}

	public String computeSign (byte[] bs)
	{
		return computeSign(bs, getSignString());
	}

	public String computeSign (byte[] key, String stringToSign)
	{

		try
		{
			byte[] keyBytes = key;
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
			Mac mac;
			mac = Mac.getInstance("HmacSHA256");
			mac.init(keySpec);
			String hmac = stringToSign;
			byte[] hmacBytes = null;

			hmacBytes = hmac.getBytes(StandardCharsets.US_ASCII);

			byte[] hmacBin = mac.doFinal(hmacBytes);

			return Hex.encodeHexString(hmacBin);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new SecurityException("HmacSHA256 not available", e);
		}
		catch (InvalidKeyException e)
		{
			throw new SecurityException("Invalid Key", e);
		}
		catch (Exception e)
		{
			throw new SecurityException("undefined error", e);
		}
	}

	public String getSignString ()
	{
		String hmac = "";
		hmac += get(MID);
		hmac += get(AMT);
		hmac += get(VS);
		hmac += get(SS);
		hmac += get(KS);
		hmac += get(RURL);

		return hmac;
	}

	public CVubPayRequest merchantId (String val)
	{
		set(MID, val);
		return this;
	}

	public CVubPayRequest sign (String val)
	{
		set(SIGN, val);
		return this;
	}

	public String sign ()
	{
		return get(SIGN);
	}

	/**
	 * Suma platby
	Suma, ktor?? m?? z??kazn??k previes?? na ????et
	obchodn??ka.
	napr. 		BigDecimal.setScale(2, RoundingMode.UP);
	desatinn?? ????slo
	- max. 9 miest pred
	odde??ova??om desat??n
	- max. 2 desatinn?? miesta
	oddelen?? bodkou
	 * @param val
	 * @return
	 */
	public CVubPayRequest amount (BigDecimal val)
	{
		set(AMT, val.toPlainString());
		return this;
	}

	/**
	 * povolen?? znaky: 0-9
	- parameter je povinn??,
	pokia?? nie je vyplnen??
	parameter REF
	- pokia?? bud?? s????asne
	vyplnen?? parametre VS
	a REF, identifik??torom
	platby bude REF a VS
	bude ignorovan??
	 * @param val
	 * @return
	 */
	public CVubPayRequest vs (String val)
	{
		set(VS, val);
		return this;
	}

	/**
	 * povolen?? znaky: 0-9
	- pokia?? bude vyplnen??
	s????asne s parametrom
	REF, bude ignorovan??
	 * @param val
	 * @return
	 */
	public CVubPayRequest ss (String val)
	{
		set(SS, val);
		return this;
	}

	/**
	 * povolen?? znaky: 0-9
	- pokia?? bude vyplnen??
	s????asne s parametrom
	REF, bude ignorovan??
	
	 * @param val
	 * @return
	 */
	public CVubPayRequest ks (String val)
	{
		set(KS, val);
		return this;
	}

	public String vs ()
	{
		return get(VS);
	}

	public String ss ()
	{
		return get(SS);
	}

	public String ks ()
	{
		return get(KS);
	}

	/**
	 * N??vratov?? URL
	URL adresa, na ktor?? banka presmeruje
	z??kazn??ka po vykonan?? platby
	 * 
	 * URL mus?? by?? vytvoren??v
	s??lade s RFC 1738 a mus??
	by?? funk??n??
	 * @param val
	 * @return
	 */
	public CVubPayRequest redirectURL (String val)
	{
		set(RURL, val);
		return this;
	}

	/**
	 * Emailov?? adresa pre zaslanie notifik??cie
	o v??sledku platby
	
	 * m????e obsahova?? iba jednu
	emailov?? adresu, platn?? v
	s??lade s RFC 2822
	- v pr??pade, ??e hodnota
	prekro???? 50 znakov,
	notifika??n?? email nebude
	odoslan??
	 * @param val
	 * @return
	 */
	public CVubPayRequest reminderEmail (String val)
	{
		set(REM, val);
		return this;
	}

	public CVubPayRequest description (String val)
	{
		set(DESC, val);
		return this;
	}

	/**
	 * slovensk?? mobiln?? ????slo obchodn??ka, kam bude zaslan?? inform??cia o realiz??cii platby. (09XXXXXXXX)
	 * @param val
	 * @return
	 */
	public CVubPayRequest reminderSMS (String val)
	{
		set(RSMS, val);
		return this;
	}

}
