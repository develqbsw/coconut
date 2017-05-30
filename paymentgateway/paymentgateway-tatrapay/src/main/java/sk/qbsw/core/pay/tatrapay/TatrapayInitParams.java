package sk.qbsw.core.pay.tatrapay;

import java.util.function.Supplier;

import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author martinkovic
 *
 */
public class TatrapayInitParams
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
	private String tatrapayGateURL = "https://moja.tatrabanka.sk/cgi-bin/e-commerce/start/tatrapay";

	// PROD prevadzka
	private String tatrapayCertsUrl = "https://moja.tatrabanka.sk/e-commerce/ecdsa_keys.txt";

	//http rest template klient 
	//sluzi na spojenie do tatrapayCertsUrl
	private RestTemplate httpClient;



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

	public String getTatrapayGateURL ()
	{
		return tatrapayGateURL;
	}

	public void setTatrapayGateURL (String tatrapayGateURL)
	{
		this.tatrapayGateURL = tatrapayGateURL;
	}

	public String getTatrapayCertsUrl ()
	{
		return tatrapayCertsUrl;
	}

	public void setTatrapayCertsUrl (String tatrapayCertsUrl)
	{
		this.tatrapayCertsUrl = tatrapayCertsUrl;
	}

	public String getApplicationCallbackURLForBank ()
	{
		return applicationCallbackURLForBank;
	}

	public void setApplicationCallbackURLForBank (String applicationCallbackURLForBank)
	{
		this.applicationCallbackURLForBank = applicationCallbackURLForBank;
	}

	public RestTemplate getHttpClient ()
	{
		return httpClient;
	}

	public void setHttpClient (RestTemplate httpClient)
	{
		this.httpClient = httpClient;
	}
}
