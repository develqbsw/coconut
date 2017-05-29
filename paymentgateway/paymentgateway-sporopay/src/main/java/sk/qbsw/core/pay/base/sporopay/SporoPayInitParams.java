/**
 * 
 */
package sk.qbsw.core.pay.base.sporopay;

import java.util.function.Supplier;

/**
 * required init params for sporopay
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class SporoPayInitParams
{
	//cislo uctu
	private String merchantAccountNumber;
	//predcislie
	private String merchantAccountPrefix;
	//cislo banky
	private String merchantBankNumber;
	
	//callback URL kde bude presmerovaný pouzivatel aj s odpovedou z banky pri schvaleni, zamietnuti platby  
	private String applicationCallbackURLForBank;

	//heslo
	private Supplier<byte[]> passwordDelegate;

	//PROD prevadzka
	private String sporopayGateURL="https://ib.slsp.sk/epayment/epayment/epayment.xml";
	

	public String getMerchantAccountNumber ()
	{
		return merchantAccountNumber;
	}

	public void setMerchantAccountNumber (String merchantAccountNumber)
	{
		this.merchantAccountNumber = merchantAccountNumber;
	}

	public String getMerchantAccountPrefix ()
	{
		return merchantAccountPrefix;
	}

	public void setMerchantAccountPrefix (String merchantAccountPrefix)
	{
		this.merchantAccountPrefix = merchantAccountPrefix;
	}

	public String getMerchantBankNumber ()
	{
		return merchantBankNumber;
	}

	public void setMerchantBankNumber (String merchantBankNumber)
	{
		this.merchantBankNumber = merchantBankNumber;
	}

	public String getApplicationCallbackURLForBank ()
	{
		return applicationCallbackURLForBank;
	}

	public void setApplicationCallbackURLForBank (String applicationCallbackURLForBank)
	{
		this.applicationCallbackURLForBank = applicationCallbackURLForBank;
	}

	public Supplier<byte[]> getPasswordDelegate ()
	{
		return passwordDelegate;
	}

	public void setPasswordDelegate (Supplier<byte[]> passwordDelegate)
	{
		this.passwordDelegate = passwordDelegate;
	}

	public String getSporopayGateURL ()
	{
		return sporopayGateURL;
	}

	public void setSporopayGateURL (String sporopayGateURL)
	{
		this.sporopayGateURL = sporopayGateURL;
	}
}
