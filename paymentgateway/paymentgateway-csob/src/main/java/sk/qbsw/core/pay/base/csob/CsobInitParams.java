/**
 * 
 */
package sk.qbsw.core.pay.base.csob;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.function.Supplier;

/**
 * required init params for sporopay
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class CsobInitParams
{
	//ID obchodnika
	private String merchantId;

	//cislo uctu
	private String merchantAccountNumber;

	//cislo banky
	private String merchantBankNumber;

	//callback URL kde bude presmerovaný pouzivatel aj s odpovedou z banky pri schvaleni, zamietnuti platby  
	private String applicationCallbackURLForBank;

	//heslo
	private Supplier<PrivateKey> privateVODCert;

	private Supplier<Certificate> publicVODCert;

	private Supplier<Certificate> publicCSOBCert;

	//PROD prevadzka
	private String csobGateURL = "https://ib24.csob.sk/Channels.aspx";

	public String getMerchantAccountNumber ()
	{
		return merchantAccountNumber;
	}

	public void setMerchantAccountNumber (String merchantAccountNumber)
	{
		this.merchantAccountNumber = merchantAccountNumber;
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

	public String getCsobGateURL ()
	{
		return csobGateURL;
	}

	public void setCsobGateURL (String sporopayGateURL)
	{
		this.csobGateURL = sporopayGateURL;
	}

	public String getMerchantId ()
	{
		return merchantId;
	}

	public void setMerchantId (String merchantId)
	{
		this.merchantId = merchantId;
	}

	/**
	 * @return the privateVODCert
	 */
	public Supplier<PrivateKey> getPrivateVODCert ()
	{
		return privateVODCert;
	}

	/**
	 * @param privateVODCert the privateVODCert to set
	 */
	public void setPrivateVODCert (Supplier<PrivateKey> privateVODCert)
	{
		this.privateVODCert = privateVODCert;
	}

	/**
	 * @return the publicVODCert
	 */
	public Supplier<Certificate> getPublicVODCert ()
	{
		return publicVODCert;
	}

	/**
	 * @param publicVODCert the publicVODCert to set
	 */
	public void setPublicVODCert (Supplier<Certificate> publicVODCert)
	{
		this.publicVODCert = publicVODCert;
	}

	/**
	 * @return the publicCSOBCert
	 */
	public Supplier<Certificate> getPublicCSOBCert ()
	{
		return publicCSOBCert;
	}

	/**
	 * @param publicCSOBCert the publicCSOBCert to set
	 */
	public void setPublicCSOBCert (Supplier<Certificate> publicCSOBCert)
	{
		this.publicCSOBCert = publicCSOBCert;
	}

}
