package sk.qbsw.core.pay.vubecard.model;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

/**
 * The Class CVubCardRequest.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public class CVubCardRequest extends CVubGeneralCall
{
	//povinne
	protected static final String clientid = "clientid";
	protected static final String storetype = "storetype";
	protected static final String hash = "hash";
	protected static final String trantype = "trantype";
	protected static final String amount = "amount";
	protected static final String currency = "currency";
	protected static final String instalment = "instalment";
	protected static final String oid = "oid";
	protected static final String okUrl = "okUrl";
	protected static final String failUrl = "failUrl";
	protected static final String lang = "lang";
	protected static final String rnd = "rnd";
	protected static final String encoding = "encoding";

	protected static final String msAuthType = "msAuthType";
	protected static final String msKey = "msKey";
	protected static final String hashAlgorithm = "hashAlgorithm";

	//volitelne
	protected static final String tel = "tel";
	protected static final String email = "email";

	//Recurring Payment [All Optional] 
	protected static final String RecurringPaymentNumber = "RecurringPaymentNumber";
	protected static final String RecurringFrequencyUnit = "RecurringFrequencyUnit";
	protected static final String RecurringFrequency = "RecurringFrequency";

	//billing optional
	protected static final String BillToCompany = "BillToCompany";
	protected static final String BillToName = "BillToName";
	protected static final String BillToStreet1 = "BillToStreet1";
	protected static final String BillToStreet2 = "BillToStreet2";
	protected static final String BillToStreet3 = "BillToStreet3";
	protected static final String BillToCity = "BillToCity";
	protected static final String BillToStateProv = "BillToStateProv";
	protected static final String BillToPostalCode = "BillToPostalCode";
	protected static final String BillToCountry = "BillToCountry";

	//shipping info optional
	protected static final String ShipToCompany = "ShipToCompany";
	protected static final String ShipToName = "ShipToName";
	protected static final String ShipToStreet1 = "ShipToStreet1";
	protected static final String ShipToStreet2 = "ShipToStreet2";
	protected static final String ShipToStreet3 = "ShipToStreet3";
	protected static final String ShipToCity = "ShipToCity";
	protected static final String ShipToStateProv = "ShipToStateProv";
	protected static final String ShipToPostalCode = "ShipToPostalCode";
	protected static final String ShipToCountry = "ShipToCountry";

	//order item optional
	protected static final String ItemNumber1 = "ItemNumber1";
	protected static final String ProductCode1 = "ProductCode1";
	protected static final String Qty1 = "Qty1";
	protected static final String Desc1 = "Desc1";
	protected static final String Id1 = "Id1";
	protected static final String Price1 = "Price1";
	protected static final String Total1 = "Total1";

	//metody 

	//povinne
	public CVubCardRequest hashAlgorithm (String val)
	{
		set(hashAlgorithm, val);
		return this;
	}

	public CVubCardRequest clientid (String val)
	{
		set(clientid, val);
		return this;
	}

	public CVubCardRequest msKey (String val)
	{
		set(msKey, val);
		return this;
	}

	public CVubCardRequest msAuthType (String val)
	{
		set(msAuthType, val);
		return this;
	}

	public CVubCardRequest storetype (String val)
	{
		set(storetype, val);
		return this;
	}

	public CVubCardRequest messageHash (String val)
	{
		set(hash, val);
		return this;
	}

	public CVubCardRequest trantype (String val)
	{
		set(trantype, val);
		return this;
	}

	public CVubCardRequest amount (String val)
	{
		set(amount, val);
		return this;
	}

	public CVubCardRequest currency (String val)
	{
		set(currency, val);
		return this;
	}

	public CVubCardRequest instalment (String val)
	{
		set(instalment, val);
		return this;
	}

	public CVubCardRequest oid (String val)
	{
		set(oid, val);
		return this;
	}

	public CVubCardRequest okUrl (String val)
	{
		set(okUrl, val);
		return this;
	}

	public CVubCardRequest failUrl (String val)
	{
		set(failUrl, val);
		return this;
	}

	public CVubCardRequest lang (String val)
	{
		set(lang, val);
		return this;
	}

	public CVubCardRequest rnd (String val)
	{
		set(rnd, val);
		return this;
	}

	public CVubCardRequest encoding (String val)
	{
		set(encoding, val);
		return this;
	}

	//volitelne
	public CVubCardRequest tel (String val)
	{
		set(tel, val);
		return this;
	}

	public CVubCardRequest email (String val)
	{
		set(email, val);
		return this;
	}

	//Recurring Payment [All Optional] 
	public CVubCardRequest RecurringPaymentNumber (String val)
	{
		set(RecurringPaymentNumber, val);
		return this;
	}

	public CVubCardRequest RecurringFrequencyUnit (String val)
	{
		set(RecurringFrequencyUnit, val);
		return this;
	}

	public CVubCardRequest RecurringFrequency (String val)
	{
		set(RecurringFrequency, val);
		return this;
	}

	//billing optional
	public CVubCardRequest BillToCompany (String val)
	{
		set(BillToCompany, val);
		return this;
	}

	public CVubCardRequest BillToName (String val)
	{
		set(BillToName, val);
		return this;
	}

	public CVubCardRequest BillToStreet1 (String val)
	{
		set(BillToStreet1, val);
		return this;
	}

	public CVubCardRequest BillToStreet2 (String val)
	{
		set(BillToStreet2, val);
		return this;
	}

	public CVubCardRequest BillToStreet3 (String val)
	{
		set(BillToStreet3, val);
		return this;
	}

	public CVubCardRequest BillToCity (String val)
	{
		set(BillToCity, val);
		return this;
	}

	public CVubCardRequest BillToStateProv (String val)
	{
		set(BillToStateProv, val);
		return this;
	}

	public CVubCardRequest BillToPostalCode (String val)
	{
		set(BillToPostalCode, val);
		return this;
	}

	public CVubCardRequest BillToCountry (String val)
	{
		set(BillToCountry, val);
		return this;
	}

	//shipping info optional
	public CVubCardRequest ShipToCompany (String val)
	{
		set(ShipToCompany, val);
		return this;
	}

	public CVubCardRequest ShipToName (String val)
	{
		set(ShipToName, val);
		return this;
	}

	public CVubCardRequest ShipToStreet1 (String val)
	{
		set(ShipToStreet1, val);
		return this;
	}

	public CVubCardRequest ShipToStreet2 (String val)
	{
		set(ShipToStreet2, val);
		return this;
	}

	public CVubCardRequest ShipToStreet3 (String val)
	{
		set(ShipToStreet3, val);
		return this;
	}

	public CVubCardRequest ShipToCity (String val)
	{
		set(ShipToCity, val);
		return this;
	}

	public CVubCardRequest ShipToStateProv (String val)
	{
		set(ShipToStateProv, val);
		return this;
	}

	public CVubCardRequest ShipToPostalCode (String val)
	{
		set(ShipToPostalCode, val);
		return this;
	}

	public CVubCardRequest ShipToCountry (String val)
	{
		set(ShipToCountry, val);
		return this;
	}

	//order item optional
	public CVubCardRequest ItemNumber1 (String val)
	{
		set(ItemNumber1, val);
		return this;
	}

	public CVubCardRequest ProductCode1 (String val)
	{
		set(ProductCode1, val);
		return this;
	}

	public CVubCardRequest Qty1 (String val)
	{
		set(Qty1, val);
		return this;
	}

	public CVubCardRequest Desc1 (String val)
	{
		set(Desc1, val);
		return this;
	}

	public CVubCardRequest Id1 (String val)
	{
		set(Id1, val);
		return this;
	}

	public CVubCardRequest Price1 (String val)
	{
		set(Price1, val);
		return this;
	}

	public CVubCardRequest Total1 (String val)
	{
		set(Total1, val);
		return this;
	}

	//povinne
	public String hashAlgorithm ()
	{
		return get(hashAlgorithm);
	}

	public String msKey ()
	{
		return get(msKey);
	}

	public String msAuthType ()
	{
		return get(msAuthType);
	}

	public String clientid ()
	{
		return get(clientid);
	}

	public String storetype ()
	{
		return get(storetype);
	}

	public String messageHash ()
	{
		return get(hash);
	}

	public String trantype ()
	{
		return get(trantype);
	}

	public String amount ()
	{
		return get(amount);
	}

	public String currency ()
	{
		return get(currency);
	}

	public String instalment ()
	{
		return get(instalment);
	}

	public String oid ()
	{
		return get(oid);
	}

	public String okUrl ()
	{
		return get(okUrl);
	}

	public String failUrl ()
	{
		return get(failUrl);
	}

	public String lang ()
	{
		return get(lang);
	}

	public String rnd ()
	{
		return get(rnd);
	}

	public String encoding ()
	{
		return get(encoding);
	}

	//volitelne
	public String tel ()
	{
		return get(tel);
	}

	public String email ()
	{
		return get(email);
	}

	//Recurring Payment [All Optional] 
	public String RecurringPaymentNumber ()
	{
		return get(RecurringPaymentNumber);
	}

	public String RecurringFrequencyUnit ()
	{
		return get(RecurringFrequencyUnit);
	}

	public String RecurringFrequency ()
	{
		return get(RecurringFrequency);
	}

	//billing optional
	public String BillToCompany ()
	{
		return get(BillToCompany);
	}

	public String BillToName ()
	{
		return get(BillToName);
	}

	public String BillToStreet1 ()
	{
		return get(BillToStreet1);
	}

	public String BillToStreet2 ()
	{
		return get(BillToStreet2);
	}

	public String BillToStreet3 ()
	{
		return get(BillToStreet3);
	}

	public String BillToCity ()
	{
		return get(BillToCity);
	}

	public String BillToStateProv ()
	{
		return get(BillToStateProv);
	}

	public String BillToPostalCode ()
	{
		return get(BillToPostalCode);
	}

	public String BillToCountry ()
	{
		return get(BillToCountry);
	}

	//shipping info optional
	public String ShipToCompany ()
	{
		return get(ShipToCompany);
	}

	public String ShipToName ()
	{
		return get(ShipToName);
	}

	public String ShipToStreet1 ()
	{
		return get(ShipToStreet1);
	}

	public String ShipToStreet2 ()
	{
		return get(ShipToStreet2);
	}

	public String ShipToStreet3 ()
	{
		return get(ShipToStreet3);
	}

	public String ShipToCity ()
	{
		return get(ShipToCity);
	}

	public String ShipToStateProv ()
	{
		return get(ShipToStateProv);
	}

	public String ShipToPostalCode ()
	{
		return get(ShipToPostalCode);
	}

	public String ShipToCountry ()
	{
		return get(ShipToCountry);
	}

	//order item optional
	public String ItemNumber1 ()
	{
		return get(ItemNumber1);
	}

	public String ProductCode1 ()
	{
		return get(ProductCode1);
	}

	public String Qty1 ()
	{
		return get(Qty1);
	}

	public String Desc1 ()
	{
		return get(Desc1);
	}

	public String Id1 ()
	{
		return get(Id1);
	}

	public String Price1 ()
	{
		return get(Price1);
	}

	public String Total1 ()
	{
		return get(Total1);
	}

	public CVubCardRequest ()
	{
		super(new HashMap<String, String>());
		//default unchanging values
		storetype("3d_pay_hosting");
		trantype("Auth");
		currency("978");
		hashAlgorithm("ver2");
		//my default 
		encoding("utf-8");
		lang("sk");

	}

	protected CVubCardRequest (Map<String, String> map)
	{
		super(map);
	}

	public String computeHash (String stringToSign)
	{
		String SIGN = "";
		try
		{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
			messageDigest.update(stringToSign.getBytes());
			String hash = new String(Base64.encodeBase64(messageDigest.digest()), "UTF-8");

			SIGN = hash;
		}
		catch (Exception e)
		{
			throw new SecurityException("", e);
		}
		return SIGN;
	}

	public String generateHashString (String storekey)
	{
		String hash = "";
		hash += getWithVbar(clientid);
		hash += getWithVbar(oid);
		hash += getWithVbar(amount);
		hash += getWithVbar(okUrl);
		hash += getWithVbar(failUrl);
		hash += getWithVbar(trantype);
		hash += getWithVbar(instalment);
		hash += getWithVbar(rnd);
		hash += getWithVbar(msAuthType);
		hash += getWithVbar(msKey);
		//TODO EMPTY  dont know what .  in documentation
		hash += FIELD_DELIMITER;
		hash += getWithVbar(currency);
		hash += storekey;
		//at end no vertical bar. 

		return hash;
	}

}
