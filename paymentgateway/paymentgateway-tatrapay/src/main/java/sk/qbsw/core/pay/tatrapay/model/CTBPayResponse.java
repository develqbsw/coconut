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
	 *Re??azcom pre v??po??et HMAC je re??azec hodn??t parametrov:
	- pokia?? bola platba identifikovan?? VS:
	AMT + CURR + VS + SS + CS + RES + TID + TIMESTAMP
	- pokia?? bola platba identifikovan?? REF:
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
	 * K??d v??sledku platby:
	OK - platba prebehla ??spe??ne
	FAIL - platba nebola ??spe??n?? alebo ju z??kazn??k zru??il
	TOUT - platba zatia?? nebola spracovan?? a banka nevie jej fin??lny v??sledok
	 * @param val
	 * @return
	 */
	public String resolution ()
	{
		return get(RES);
	}

	/**
	 * Jednozna??n?? identifik??tor platby na strane banky
	Pomocou tohto identifik??tora je mo??n?? jednoducho opakovane overi?? stav
	platby v pr??pade TOUT prostredn??ctvom rozhrania ???Overenie stavov platieb
	vykonan??ch cez TatraPay???.
	Parameter sa v odpovedi nach??dza, pokia?? je v??sledok platby OK alebo
	TOUT. V pr??pade FAIL iba vtedy, ak bola po??iadavka platn??, z??kazn??k sa
	??spe??ne prihl??sil a potvrdil platbu
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
