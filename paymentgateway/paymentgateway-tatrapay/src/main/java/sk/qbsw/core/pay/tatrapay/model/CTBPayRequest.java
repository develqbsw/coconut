package sk.qbsw.core.pay.tatrapay.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import sk.qbsw.core.pay.base.util.PaymentFormatUtils;

/**
 * The Class CTBPayRequest.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public class CTBPayRequest extends CTBGeneralCall
{
	private static final String HMAC = "HMAC";
	private static final String AREDIR = "AREDIR";
	private static final String LANG = "LANG";
	private static final String REF = "REF";
	private static final String TIMESTAMP = "TIMESTAMP";
	private static final String REM = "REM";
	private static final String RURL = "RURL";
	private static final String KS = "CS";
	private static final String SS = "SS";
	private static final String VS = "VS";
	private static final String CURR = "CURR";
	private static final String AMT = "AMT";
	private static final String MID = "MID";

	public CTBPayRequest ()
	{
		super(new HashMap<String, String>());
	}

	protected CTBPayRequest (Map<String, String> map)
	{
		super(map);
	}

	/**
	 *Autentifikačný kód HMAC z parametrov:
	- pokiaľ je platba identifikovaná VS:
	MID + AMT + CURR + VS + SS + CS +
	RURL + REM + TIMESTAMP
	- pokiaľ je platba identifikovaná REF:
	MID + AMT + CURR + REF + RURL +
	REM + TIMESTAMP
	 * @return
	 */
	@Override
	public String getHmacString ()
	{
		String hmac = "";
		if (StringUtils.isEmpty(get(REF)))
		{
			hmac += get(MID);
			hmac += get(AMT);
			hmac += get(CURR);
			hmac += get(VS);
			hmac += get(SS);
			hmac += get(KS);
			hmac += get(RURL);
			hmac += get(REM);
			hmac += get(TIMESTAMP);

		}
		else
		{
			hmac += get(MID);
			hmac += get(AMT);
			hmac += get(CURR);
			hmac += get(REF);
			hmac += get(RURL);
			hmac += get(REM);
			hmac += get(TIMESTAMP);
		}

		return hmac;
	}

	public CTBPayRequest merchantId (String val)
	{
		set(MID, val);
		return this;
	}

	/**
	 * Suma platby
	Suma, ktorú má zákazník previesť na účet
	obchodníka.
	napr. 		BigDecimal.setScale(2, RoundingMode.UP);
	desatinné číslo
	- max. 9 miest pred
	oddeľovačom desatín
	- max. 2 desatinné miesta
	oddelené bodkou
	 * @param val
	 * @return
	 */
	public CTBPayRequest amount (BigDecimal val)
	{
		//Otestovat
		set(AMT, PaymentFormatUtils.normalizeAmountAndConvert(val));
		return this;
	}

	public CTBPayRequest currency ()
	{
		//978 == euro
		set(CURR, "978");
		return this;
	}

	/**
	 * povolené znaky: 0-9
	- parameter je povinný,
	pokiaľ nie je vyplnený
	parameter REF
	- pokiaľ budú súčasne
	vyplnené parametre VS
	a REF, identifikátorom
	platby bude REF a VS
	bude ignorovaný
	 * @param val
	 * @return
	 */
	public CTBPayRequest vs (String val)
	{
		set(VS, val);
		return this;
	}

	/**
	 * povolené znaky: 0-9
	- pokiaľ bude vyplnený
	súčasne s parametrom
	REF, bude ignorovaný
	 * @param val
	 * @return
	 */
	public CTBPayRequest ss (String val)
	{
		set(SS, val);
		return this;
	}

	/**
	 * povolené znaky: 0-9
	- pokiaľ bude vyplnený
	súčasne s parametrom
	REF, bude ignorovaný
	
	 * @param val
	 * @return
	 */
	public CTBPayRequest ks (String val)
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
	 * povolené znaky: 0-9 A-Z
	a-z . - / a medzera
	- parameter je povinný,
	pokiaľ nie je vyplnený
	parameter VS
	 * @param val
	 * @return
	 */
	public CTBPayRequest reference (String val)
	{
		set(REF, val);
		return this;
	}

	/**
	 * Návratová URL
	URL adresa, na ktorú banka presmeruje
	zákazníka po vykonaní platby
	 * 
	 * URL musí byť vytvorenáv
	súlade s RFC 1738 a musí
	byť funkčná
	 * @param val
	 * @return
	 */
	public CTBPayRequest redirectURL (String val)
	{
		set(RURL, val);
		return this;
	}

	/**
	 * Emailová adresa pre zaslanie notifikácie
	o výsledku platby
	
	 * môže obsahovať iba jednu
	emailovú adresu, platnú v
	súlade s RFC 2822
	- v prípade, že hodnota
	prekročí 50 znakov,
	notifikačný email nebude
	odoslaný
	 * @param val
	 * @return
	 */
	public CTBPayRequest reminderEmail (String val)
	{
		set(REM, val);
		return this;
	}

	/*
	 * Timestamp (časová pečiatka) v UTC
	Server banky spracuje iba požiadavky,
	ktoré budú mať TIMESTAMP v intervale
	+/- 1 hodina voči UTC (GMT)
	DDMMYYYYHHMISS
	 */
	DateTimeFormatter timestampFormatter = DateTimeFormat.forPattern("ddMMYYYYHHmmSS").withZoneUTC();

	public CTBPayRequest timestamp (DateTime val)
	{

		set(TIMESTAMP, timestampFormatter.print(val));
		return this;
	}

	/**
	 * Autentifikačný kód HMAC z parametrov:
	- pokiaľ je platba identifikovaná VS:
	MID + AMT + CURR + VS + SS + CS +
	RURL + REM + TIMESTAMP
	- pokiaľ je platba identifikovaná REF:
	MID + AMT + CURR + REF + RURL +
	REM + TIMESTAMP
	 * @param val
	 * @return
	 */
	public CTBPayRequest hmac (String val)
	{
		set(HMAC, val);
		return this;
	}

	public String hmac ()
	{
		return get(HMAC);
	}

	/**
	 * Príznak automatického presmerovania na
	stránku obchodníka (RURL) po 9-tich
	sekundách.
	Možnosti:
	- false (predvolená): vypnuté t.j. zákazník musí
	stlačiť tlačidlo „Pokračovať“
	- true : zapnuté
	 * @param val
	 * @return
	 */
	public CTBPayRequest redirectTimeout (boolean enabled)
	{
		set(AREDIR, enabled ? "1" : "0");
		return this;
	}

	/**
	 * Kód jazyka, v ktorom bude zobrazená
	aplikácia TatraPay
	Možnosti:
	- sk (predvolená): slovenčina
	- en: anglický jazyk
	 * @param val
	 * @return
	 */
	public CTBPayRequest guiLang (String val)
	{
		set(LANG, val);
		return this;
	}

	public CTBPayRequest timestamp (String val)
	{
		set(TIMESTAMP, val);
		return this;
	}

}
