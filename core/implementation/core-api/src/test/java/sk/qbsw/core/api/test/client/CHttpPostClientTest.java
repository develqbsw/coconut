package sk.qbsw.core.api.test.client;

import org.apache.http.HttpHost;
import org.junit.Ignore;
import org.junit.Test;

import sk.qbsw.core.api.client.AApiClient;
import sk.qbsw.core.api.client.CApiClient;
import sk.qbsw.core.api.client.http.CHttpApiPostRequest;

/**
 * Creates test call to mscan.qsbw.local (Be sure, that the link is available
 * without proxy settings)
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 * 
 */
public class CHttpPostClientTest
{
	/**
	 * Tests connection without proxy settings
	 * @throws Exception 
	 */
	@Test
	public void testNoProxy () throws Exception
	{
		AApiClient<CCallRequestModel, CCallResponseModel> client = new CApiClient<CCallRequestModel, CCallResponseModel>();
		CHttpApiPostRequest post = new CHttpApiPostRequest();
		post.setRepeatCount(2);
		post.setTimeout(10000);

		client.makeCall(post, "http://mscan.qbsw.local/admin/index.html", new CCallRequestModel());
	}

	/**
	 * Tests connection with proxy settings
	 * @throws Exception 
	 */
	@Ignore
	@Test
	public void testWithProxy () throws Exception
	{
		AApiClient<CCallRequestModel, CCallResponseModel> client = new CApiClient<CCallRequestModel, CCallResponseModel>();
		CHttpApiPostRequest post = new CHttpApiPostRequest();
		post.setRepeatCount(2);
		post.setTimeout(10000);
		post.setProxy(new HttpHost("192.168.121.31", 3128, "http"));

		client.makeCall(post, "http://www.sme.sk", new CCallRequestModel());
	}

}
