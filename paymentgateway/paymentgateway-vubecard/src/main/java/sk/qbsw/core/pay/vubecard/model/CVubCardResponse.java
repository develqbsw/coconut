package sk.qbsw.core.pay.vubecard.model;

import java.util.Map;

/**
 * regexp ktory mi pomohli pri generovanie tychto gettrov a settrov 
 * 
 * 
 * find: 
 * 
 * replace :
 * public String $1 \(\)\{return get\($1\);\}
 * public CVubCardResponse $1 \(String val\)\{set\($1, val\);return this;\}.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public class CVubCardResponse extends CVubCardRequest
{
	protected String Response = "Response"; // „Approved“, „Declined“ alebo „Error“
	protected String AuthCode = "AuthCode"; // Autorizačný kód transakcie
	protected String HostRefNum = "HostRefNum"; // Referenčné číslo hostiteľa
	protected String ProcReturnCode = "ProcReturnCode"; // Kód stavu transakcie
	protected String TransId = "TransId"; // Jedinečné ID transakcie
	protected String ErrMsg = "ErrMsg"; // Text chyby (ak má parameter Response hodnotu „Declined“ alebo „Error“ )
	protected String ClientIp = "ClientIp"; // Adresa IP zákazníka
	protected String ReturnOid = "ReturnOid"; // Vrátené ID príkazu, musí byť rovnaké ako vstupné oid.
	protected String MaskedPan = "MaskedPan"; // Maskované číslo kreditnej karty
	protected String PaymentMethod = "PaymentMethod"; // Spôsob platby transakcie
	protected String EXTRA_CARDBRAND = "EXTRA.CARDBRAND";// Typ kreditnej karty (Visa, MasterCard, etc…)
	protected String rnd = "rnd"; // Náhodný reťazec, použije sa na porovnanie hodnoty hash
	protected String HASHPARAMS = "HASHPARAMS"; // Obsahuje názvy polí použitých na výpočet hodnoty hash. Názvy polí budú pripojené znakom „:“
	protected String HASHPARAMSVAL = "HASHPARAMSVAL"; // Obsahuje spojené hodnoty polí hash na výpočet hodnoty hash. Hodnoty polí sú spojené v rovnakom poradí ako v poli HASHPARAMS
	protected String HASH = "HASH"; // Hodnota hash poľa HASHPARAMSVAL a poľa s heslom obchodníka 

	//pre kartove operacie
	protected String mdStatus = "mdStatus"; //Kód stavu transakcie 3D
	protected String txstatus = "txstatus"; //stav 3D na archiváciu
	protected String eci = "eci"; //Indikátor elektronického obchodu
	protected String cavv = "cavv"; //Hodnota overenia držiteľa karty, určená ACS.
	protected String mdErrorMsg = "mdErrorMsg"; //Hodnota hash nahradzujúca číslo karty
	protected String xid = "xid"; //Chybové hlásenie z MPI

	public CVubCardResponse (Map<String, String> params)
	{

		super(params);
	}

	public CVubCardResponse Response (String val)
	{
		set(Response, val);
		return this;
	}

	public CVubCardResponse AuthCode (String val)
	{
		set(AuthCode, val);
		return this;
	}

	public CVubCardResponse HostRefNum (String val)
	{
		set(HostRefNum, val);
		return this;
	}

	public CVubCardResponse ProcReturnCode (String val)
	{
		set(ProcReturnCode, val);
		return this;
	}

	public CVubCardResponse TransId (String val)
	{
		set(TransId, val);
		return this;
	}

	public CVubCardResponse ErrMsg (String val)
	{
		set(ErrMsg, val);
		return this;
	}

	public CVubCardResponse ClientIp (String val)
	{
		set(ClientIp, val);
		return this;
	}

	public CVubCardResponse ReturnOid (String val)
	{
		set(ReturnOid, val);
		return this;
	}

	public CVubCardResponse MaskedPan (String val)
	{
		set(MaskedPan, val);
		return this;
	}

	public CVubCardResponse PaymentMethod (String val)
	{
		set(PaymentMethod, val);
		return this;
	}

	public CVubCardResponse EXTRA_CARDBRAND (String val)
	{
		set(EXTRA_CARDBRAND, val);
		return this;
	}

	@Override
	public CVubCardResponse rnd (String val)
	{
		set(rnd, val);
		return this;
	}

	public CVubCardResponse HASHPARAMS (String val)
	{
		set(HASHPARAMS, val);
		return this;
	}

	public CVubCardResponse HASHPARAMSVAL (String val)
	{
		set(HASHPARAMSVAL, val);
		return this;
	}

	public CVubCardResponse HASH (String val)
	{
		set(HASH, val);
		return this;
	}

	public CVubCardResponse mdStatus (String val)
	{
		set(mdStatus, val);
		return this;
	}

	public CVubCardResponse txstatus (String val)
	{
		set(txstatus, val);
		return this;
	}

	public CVubCardResponse eci (String val)
	{
		set(eci, val);
		return this;
	}

	public CVubCardResponse cavv (String val)
	{
		set(cavv, val);
		return this;
	}

	public CVubCardResponse mdErrorMsg (String val)
	{
		set(mdErrorMsg, val);
		return this;
	}

	public CVubCardResponse xid (String val)
	{
		set(xid, val);
		return this;
	}

	public String Response ()
	{
		return get(Response);
	}

	public String AuthCode ()
	{
		return get(AuthCode);
	}

	public String HostRefNum ()
	{
		return get(HostRefNum);
	}

	public String ProcReturnCode ()
	{
		return get(ProcReturnCode);
	}

	public String TransId ()
	{
		return get(TransId);
	}

	public String ErrMsg ()
	{
		return get(ErrMsg);
	}

	public String ClientIp ()
	{
		return get(ClientIp);
	}

	public String ReturnOid ()
	{
		return get(ReturnOid);
	}

	public String MaskedPan ()
	{
		return get(MaskedPan);
	}

	public String PaymentMethod ()
	{
		return get(PaymentMethod);
	}

	public String EXTRA_CARDBRAND ()
	{
		return get(EXTRA_CARDBRAND);
	}

	@Override
	public String rnd ()
	{
		return get(rnd);
	}

	public String HASHPARAMS ()
	{
		return get(HASHPARAMS);
	}

	public String HASHPARAMSVAL ()
	{
		return get(HASHPARAMSVAL);
	}

	public String HASH ()
	{
		return get(HASH);
	}

	public String mdStatus ()
	{
		return get(mdStatus);
	}

	public String txstatus ()
	{
		return get(txstatus);
	}

	public String eci ()
	{
		return get(eci);
	}

	public String cavv ()
	{
		return get(cavv);
	}

	public String mdErrorMsg ()
	{
		return get(mdErrorMsg);
	}

	public String xid ()
	{
		return get(xid);
	}

	/**
	 * Hashovaný text obchodníka sa vygeneruje z parametrov clientid, oid, ProcReturnCode, Response,
	rnd (a kľúča obchodníka - storekey, ktorý je tajný prvok hodnoty hash).
	 * @param params
	 */
	public String generateHashParamsString (String storekey)
	{
		StringBuilder hash = new StringBuilder();
		for (String paramName : HASHPARAMS().split("\\|"))
		{
			hash.append(getWithVbar(paramName));
		}
		hash.append(storekey);
		//at end no vertical bar. 
		return hash.toString();
	}

}
