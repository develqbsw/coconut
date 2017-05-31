package sk.qbsw.core.pay.base.sporopay.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class SporoPayRequest.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public class SporoPayRequest extends SporoPayGeneralCall
{
	protected static final String PARAM = "param";
	protected static final String PREDCISLO_UCTU = "pu_predcislo";
	protected static final String CISLO_UCTU = "pu_cislo";
	protected static final String KBANKY = "pu_kbanky";
	protected static final String SUMA = "suma";
	protected static final String MENA = "mena";
	protected static final String URL = "url";
	protected static final String SS = "ss";
	protected static final String VS = "vs";
	protected static final String SIGN = "sign1";
	protected static final String PAYMENT_TYPE = "payment_type";
	protected static final String VPOS = "vpos";
	protected static final String LATE_PRESENTMENT = "late_presentment";

	public SporoPayRequest ()
	{
		super(new HashMap<String, String>());
	}

	protected SporoPayRequest (Map<String, String> map)
	{
		super(map);
	}

	public String getSign1String ()
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
		//		sign += getWithSemicolumn(PAYMENT_TYPE);
		//		sign += getWithSemicolumn(VPOS);
		//		sign += getWithSemicolumn(LATE_PRESENTMENT);

		//remove last ;
		sign = StringUtils.chomp(sign);
		sign = StringUtils.removeEnd(sign, ";");
		return sign;

	}

	//public SporoPayRequest $1  (String val){set($1, val);return this;}

	public SporoPayRequest param (String val)
	{
		set(PARAM, val);
		return this;
	}

	public SporoPayRequest predcislo_uctu (String val)
	{
		set(PREDCISLO_UCTU, val);
		return this;
	}

	public SporoPayRequest cislo_uctu (String val)
	{
		set(CISLO_UCTU, val);
		return this;
	}

	public SporoPayRequest kbanky (String val)
	{
		set(KBANKY, val);
		return this;
	}

	public SporoPayRequest suma (String val)
	{
		set(SUMA, val);
		return this;
	}

	public SporoPayRequest mena (String val)
	{
		set(MENA, val);
		return this;
	}

	public SporoPayRequest url (String val)
	{
		set(URL, val);
		return this;
	}

	public SporoPayRequest ss (String val)
	{
		set(SS, val);
		return this;
	}

	public SporoPayRequest vs (String val)
	{
		set(VS, val);
		return this;
	}

	public SporoPayRequest sign (String val)
	{
		set(SIGN, val);
		return this;
	}

	public SporoPayRequest payment_type (String val)
	{
		set(PAYMENT_TYPE, val);
		return this;
	}

	public SporoPayRequest vpos (String val)
	{
		set(VPOS, val);
		return this;
	}

	public SporoPayRequest late_presentment (String val)
	{
		set(LATE_PRESENTMENT, val);
		return this;
	}

	public String param ()
	{
		return get(PARAM);
	}

	public String predcislo_uctu ()
	{
		return get(PREDCISLO_UCTU);
	}

	public String cislo_uctu ()
	{
		return get(CISLO_UCTU);
	}

	public String kbanky ()
	{
		return get(KBANKY);
	}

	public String suma ()
	{
		return get(SUMA);
	}

	public String mena ()
	{
		return get(MENA);
	}

	public String url ()
	{
		return get(URL);
	}

	public String ss ()
	{
		return get(SS);
	}

	public String vs ()
	{
		return get(VS);
	}

	public String sign ()
	{
		return get(SIGN);
	}

	public String payment_type ()
	{
		return get(PAYMENT_TYPE);
	}

	public String vpos ()
	{
		return get(VPOS);
	}

	public String late_presentment ()
	{
		return get(LATE_PRESENTMENT);
	}

	public String computeSign1 (byte[] bs)
	{
		return computeHash(bs, getSign1String());
	}

	public String computeHash (byte[] key, String stringToSign)
	{
		try
		{
			byte[] keyBytes = key;//Base64.decodeBase64(key);

			//expand key
			keyBytes = Arrays.copyOf(keyBytes, 24);
			for (int j = 0, k = 16; j < 8;)
			{
				keyBytes[k++] = keyBytes[j++];
			}
			//expanded

			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "DESede");

			//CBC
			Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding", "SunJCE");

			byte[] ivArray = new byte[8];
			Arrays.fill(ivArray, (byte) 0);
			IvParameterSpec iv = new IvParameterSpec(ivArray);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

			//https://billstclair.com/grabbe/3des1_cbc.htm

			byte[] hashBytes = getSha1Hash(stringToSign);
			hashBytes = Arrays.copyOf(hashBytes, 24);
			hashBytes[20] = (byte) 0xFF;
			hashBytes[21] = (byte) 0xFF;
			hashBytes[22] = (byte) 0xFF;
			hashBytes[23] = (byte) 0xFF;

			byte[] sigBin = cipher.doFinal(hashBytes);

			return Base64.getEncoder().encodeToString(sigBin);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new SecurityException("HmacSHA256 not available", e);
		}
		catch (InvalidKeyException e)
		{
			throw new SecurityException("Invalid Key", e);
		}
		catch (InvalidAlgorithmParameterException e)
		{
			throw new SecurityException("Invalid algorithm param IV", e);
		}
		catch (IllegalBlockSizeException e)
		{
			throw new SecurityException("encryption error", e);
		}
		catch (BadPaddingException e)
		{
			throw new SecurityException("algorithm padding not available", e);
		}
		catch (NoSuchPaddingException e)
		{
			throw new SecurityException("algorithm padding not available", e);
		}
		catch (NoSuchProviderException e)
		{
			throw new SecurityException("no provider", e);
		}
	}

	private byte[] getSha1Hash (String sign)
	{
		try
		{//or HmacSHA1 
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest;
			digest = md.digest(sign.getBytes("utf-8"));
			return digest;
		}
		catch (UnsupportedEncodingException e)
		{
			throw new SecurityException("unsupported encoding UTF8", e);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new SecurityException("SHA-1 not available", e);
		}
	}

}
