package sk.qbsw.core.pay.tatrapay.model;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import sk.qbsw.core.pay.base.response.BankResponse;

/**
 * The Class CTBGeneralCall.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public abstract class CTBGeneralCall extends BankResponse
{

	public CTBGeneralCall (Map<String, String> params)
	{
		super(params);

	}

	/**
	 * vypocita HMAC pre tuto platbu. 
	 * @param bs 
	 * @return
	 */
	public String computeHMAC (byte[] bs)
	{
		try
		{
			byte[] keyBytes = bs;
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
			Mac mac;
			mac = Mac.getInstance("HmacSHA256");
			mac.init(keySpec);
			String hmac = getHmacString();
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
	}

	public abstract String getHmacString ();

}
