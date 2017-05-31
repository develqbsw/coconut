package sk.qbsw.core.pay.tatrapay.model;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class CTBPayResponse.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public class CTBPayResponse extends CTBPayRequest
{

	private static final String HMAC = "HMAC";
	private static final String ECDSA_KEY = "ECDSA_KEY";
	private static final String ECDSA = "ECDSA";
	private static final String TID = "TID";
	private static final String RES = "RES";
	private static final String REF = "REF";
	private static final String TIMESTAMP = "TIMESTAMP";
	private static final String KS = "CS";
	private static final String SS = "SS";
	private static final String VS = "VS";
	private static final String CURR = "CURR";
	private static final String AMT = "AMT";

	public CTBPayResponse (Map<String, String> params)
	{
		super(params);
	}

	/**
	 * overi sa ECDSA pre tuto platbu. 
	 * @param key 
	 * @return
	 */
	public boolean verifyECDSA (String publicKey)
	{
		try
		{
			//			publicKey = publicKey.replaceAll("-----(BEGIN|END).*", "").trim();
			X509EncodedKeySpec spec = new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(publicKey));
			KeyFactory keyFactory = KeyFactory.getInstance("EC");
			PublicKey pKey = keyFactory.generatePublic(spec);
			Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
			ecdsaSign.initVerify(pKey);
			ecdsaSign.update(getEcsdaString().getBytes("UTF-8"));
			if (ecdsaSign.verify(new BigInteger(ecdsa(), 16).toByteArray()))
			{
				return true;
			}
			return false;

		}
		catch (SignatureException e)
		{
			throw new SecurityException("", e);
		}
		catch (InvalidKeyException e)
		{
			throw new SecurityException("", e);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new SecurityException("", e);
		}
		catch (InvalidKeySpecException e)
		{
			throw new SecurityException("", e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new SecurityException("", e);
		}

	}

	/**
	 *Reťazcom pre výpočet HMAC je reťazec hodnôt parametrov:
	- pokiaľ bola platba identifikovaná VS:
	AMT + CURR + VS + SS + CS + RES + TID + TIMESTAMP
	- pokiaľ bola platba identifikovaná REF:
	AMT + CURR + REF + RES + TID + TIMESTAMP
	 * @return
	 */
	@Override
	public String getHmacString ()
	{
		String hmac = "";
		if (StringUtils.isEmpty(get(REF)))
		{
			hmac += get(AMT);
			hmac += get(CURR);
			hmac += get(VS);
			hmac += get(SS);
			hmac += get(KS);
			hmac += get(RES);
			hmac += get(TID);
			hmac += get(TIMESTAMP);

		}
		else
		{
			hmac += get(AMT);
			hmac += get(CURR);
			hmac += get(REF);
			hmac += get(RES);
			hmac += get(TID);
			hmac += get(TIMESTAMP);
		}
		return hmac;
	}

	public String getEcsdaString ()
	{
		String ecdsa = "";
		if (StringUtils.isEmpty(get(REF)))
		{
			ecdsa += get(AMT);
			ecdsa += get(CURR);
			ecdsa += get(VS);
			ecdsa += get(SS);
			ecdsa += get(KS);
			ecdsa += get(RES);
			ecdsa += get(TID);
			ecdsa += get(TIMESTAMP);
			ecdsa += get(HMAC);
		}
		else
		{
			ecdsa += get(AMT);
			ecdsa += get(CURR);
			ecdsa += get(REF);
			ecdsa += get(RES);
			ecdsa += get(TID);
			ecdsa += get(TIMESTAMP);
			ecdsa += get(HMAC);
		}
		return ecdsa;

	}

	/**
	 * Kód výsledku platby:
	OK - platba prebehla úspešne
	FAIL - platba nebola úspešná alebo ju zákazník zrušil
	TOUT - platba zatiaľ nebola spracovaná a banka nevie jej finálny výsledok
	 * @param val
	 * @return
	 */
	public String resolution ()
	{
		return get(RES);
	}

	/**
	 * Jednoznačný identifikátor platby na strane banky
	Pomocou tohto identifikátora je možné jednoducho opakovane overiť stav
	platby v prípade TOUT prostredníctvom rozhrania „Overenie stavov platieb
	vykonaných cez TatraPay“.
	Parameter sa v odpovedi nachádza, pokiaľ je výsledok platby OK alebo
	TOUT. V prípade FAIL iba vtedy, ak bola požiadavka platná, zákazník sa
	úspešne prihlásil a potvrdil platbu
	 * @return
	 */
	public String tid ()
	{
		return get(TID);
	}

	public String ecdsaKey ()
	{
		return get(ECDSA_KEY);
	}

	public String ecdsa ()
	{
		return get(ECDSA);
	}

}
