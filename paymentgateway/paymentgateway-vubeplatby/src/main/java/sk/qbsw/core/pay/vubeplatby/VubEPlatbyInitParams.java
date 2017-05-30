package sk.qbsw.core.pay.vubeplatby;

import java.util.function.Supplier;

import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author martinkovic
 *
 */
public class VubEPlatbyInitParams
{
	// cislo zakaznika v Banke
	private String merchantId;

	// email kam bude notifikovany vlastnik uctu.
	private String notifyEmail;

	// callback URL kde bude presmerovaný pouzivatel aj s odpovedou z banky pri
	// schvaleni, zamietnuti platby
	private String applicationCallbackURLForBank;

	// heslo
	private Supplier<byte[]> passwordDelegate;

	// PROD prevadzka
	// test prevadzka https://nib.vub.sk/epay/merchant
	private String vubeplatbyGateURL = "https://ib.vub.sk/e-platbyeuro.aspx";

	public Supplier<byte[]> getPasswordDelegate ()
	{
		return passwordDelegate;
	}

	public void setPasswordDelegate (Supplier<byte[]> passwordDelegate)
	{
		this.passwordDelegate = passwordDelegate;
	}


	public String getNotifyEmail ()
	{
		return notifyEmail;
	}

	public void setNotifyEmail (String notifyEmail)
	{
		this.notifyEmail = notifyEmail;
	}

	public String getMerchantId ()
	{
		return merchantId;
	}

	public void setMerchantId (String merchantId)
	{
		this.merchantId = merchantId;
	}



	public String getApplicationCallbackURLForBank ()
	{
		return applicationCallbackURLForBank;
	}

	public void setApplicationCallbackURLForBank (String applicationCallbackURLForBank)
	{
		this.applicationCallbackURLForBank = applicationCallbackURLForBank;
	}

	public String getVubeplatbyGateURL ()
	{
		return vubeplatbyGateURL;
	}

	public void setVubeplatbyGateURL (String vubeplatbyGateURL)
	{
		this.vubeplatbyGateURL = vubeplatbyGateURL;
	}


}
