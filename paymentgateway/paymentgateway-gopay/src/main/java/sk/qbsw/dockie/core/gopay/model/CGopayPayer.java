package sk.qbsw.dockie.core.gopay.model;

/**
 * The Class CGopayPayer.
 *
 * @author martinkovic
 * @version 1.7.0
 * @since  1.7.0
 */
public class CGopayPayer
{
	/**
	 * 
	 * 
	 * PAYMENT_CARD 	Platební karty
	BANK_ACCOUNT 	Bankovní převody
	PRSMS 	Premium SMS
	MPAYMENT 	Mplatba
	PAYSAFECARD 	paysafecard
	SUPERCASH 	superCASH
	GOPAY 	GoPay účet
	PAYPAL 	PayPal účet
	 */
	private String[] allowed_payment_instruments;
	private String default_payment_instrument;

	/*
	 * GIBACZPX 	Česká spořitelna
	​​KOMBCZPP 	Komerční Banka
	RZBCCZPP 	Raiffeisenbank
	BREXCZPP 	mBank
	FIOBCZPP 	FIO Banka
	CEKOCZPP 	ČSOB
	CEKOCZPP-ERA 	ERA
	SUBASKBX 	Všeobecná úverová banka Banka
	TATRSKBX 	Tatra Banka
	UNCRSKBX 	Unicredit Bank SK
	GIBASKBX 	Slovenská spořitelna
	OTPVSKBX 	OTP Banka
	POBNSKBA 	Poštová Banka
	CEKOSKBX 	ČSOB SK
	LUBASKBX 	Sberbank Slovensko
	 */
	private String default_swift;
	private String[] allowed_swifts;
	private CGopayContact contact;

	public String getDefault_payment_instrument ()
	{
		return default_payment_instrument;
	}

	public void setDefault_payment_instrument (String default_payment_instrument)
	{
		this.default_payment_instrument = default_payment_instrument;
	}

	public String getDefault_swift ()
	{
		return default_swift;
	}

	public void setDefault_swift (String default_swift)
	{
		this.default_swift = default_swift;
	}

	public CGopayContact getContact ()
	{
		return contact;
	}

	public void setContact (CGopayContact contact)
	{
		this.contact = contact;
	}

	public String[] getAllowed_payment_instruments ()
	{
		return allowed_payment_instruments;
	}

	public void setAllowed_payment_instruments (String[] allowed_payment_instruments)
	{
		this.allowed_payment_instruments = allowed_payment_instruments;
	}

	public String[] getAllowed_swifts ()
	{
		return allowed_swifts;
	}

	public void setAllowed_swifts (String[] allowed_swifts)
	{
		this.allowed_swifts = allowed_swifts;
	}

}
