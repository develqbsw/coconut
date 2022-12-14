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
public class GoPayInitParams
{
	//ID merchanta
	private Long merchantId;
	//heslo
	private Supplier<String> apiKey;
	
	//heslo
	private Supplier<String> apiSecret;
	
	
	//callback URL kde bude presmerovanÃ½ pouzivatel aj s odpovedou z banky pri schvaleni, zamietnuti platby  
	private String applicationCallbackURLForBank;


	//PROD prevadzka
	//sandbox address https://gw.sandbox.gopay.com/api
	private String gopayGateURL="https://gate.gopay.cz/api";


	public Long getMerchantId ()
	{
		return merchantId;
	}


	public void setMerchantId (Long merchantId)
	{
		this.merchantId = merchantId;
	}


	public Supplier<String> getApiKey ()
	{
		return apiKey;
	}


	public void setApiKey (Supplier<String> apiKey)
	{
		this.apiKey = apiKey;
	}


	public Supplier<String> getApiSecret ()
	{
		return apiSecret;
	}


	public void setApiSecret (Supplier<String> apiSecret)
	{
		this.apiSecret = apiSecret;
	}


	public String getApplicationCallbackURLForBank ()
	{
		return applicationCallbackURLForBank;
	}


	public void setApplicationCallbackURLForBank (String applicationCallbackURLForBank)
	{
		this.applicationCallbackURLForBank = applicationCallbackURLForBank;
	}


	public String getGopayGateURL ()
	{
		return gopayGateURL;
	}


	public void setGopayGateURL (String gopayGateURL)
	{
		this.gopayGateURL = gopayGateURL;
	}
	
}
