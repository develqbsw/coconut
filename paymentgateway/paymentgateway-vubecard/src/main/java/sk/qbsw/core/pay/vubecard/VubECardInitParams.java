package sk.qbsw.core.pay.vubecard;

import java.util.function.Supplier;

import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author martinkovic
 *
 */
public class VubECardInitParams
{
	// cislo zakaznika v Banke
	private String merchantId;

	// callback URL kde bude presmerovaný pouzivatel aj s odpovedou z banky pri
	// schvaleni platby
	private String callbackOkUrl;
	// callback URL kde bude presmerovaný pouzivatel aj s odpovedou z banky pri
	// zamietnuti platby
	private String callbackFailUrl;

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



	public String getMerchantId ()
	{
		return merchantId;
	}

	public void setMerchantId (String merchantId)
	{
		this.merchantId = merchantId;
	}



	public String getCallbackOkUrl ()
	{
		return callbackOkUrl;
	}

	public void setCallbackOkUrl (String url)
	{
		this.callbackOkUrl = url;
	}

	public String getVubeplatbyGateURL ()
	{
		return vubeplatbyGateURL;
	}

	public void setVubeplatbyGateURL (String vubeplatbyGateURL)
	{
		this.vubeplatbyGateURL = vubeplatbyGateURL;
	}

	public String getCallbackFailUrl ()
	{
		return callbackFailUrl;
	}

	public void setCallbackFailUrl (String callbackFailUrl)
	{
		this.callbackFailUrl = callbackFailUrl;
	}


}
