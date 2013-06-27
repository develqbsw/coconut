package sk.qbsw.core.api.test.client;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.junit.Ignore;
import org.junit.Test;

import sk.qbsw.core.api.client.CApiClient;
import sk.qbsw.core.api.client.http.CHttpApiGetRequest;

/**
 * Creates test call to mscan.qsbw.local (Be sure, that the link is available
 * without proxy settings)
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 * 
 */
public class CHttpGetClientTest
{
	/**
	 * Tests connection without proxy
	 */
	@Test
	public void testNoProxy () throws IOException
	{
		CApiClient<CCallRequestModel, CCallResponseModel> client = new CApiClient<CCallRequestModel, CCallResponseModel>();
		CHttpApiGetRequest get = new CHttpApiGetRequest();
		get.setRepeatCount(2);
		get.setTimeout(10000);

		client.makeCall(get, "http://mscan.qbsw.local/admin/index.html", new CCallRequestModel());
	}

	/**
	 * Tests connection with proxy settings
	 */
	@Ignore
	@Test
	public void testWithProxy () throws IOException
	{
		CApiClient<CCallRequestModel, CCallResponseModel> client = new CApiClient<CCallRequestModel, CCallResponseModel>();
		CHttpApiGetRequest get = new CHttpApiGetRequest();
		get.setRepeatCount(2);
		get.setTimeout(10000);
		get.setProxy(new HttpHost("192.168.121.31", 3128));

		client.makeCall(get, "http://www.sme.sk", new CCallRequestModel());
	}

}
