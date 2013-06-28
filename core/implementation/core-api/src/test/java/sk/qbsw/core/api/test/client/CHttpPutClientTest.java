package sk.qbsw.core.api.test.client;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.junit.Ignore;
import org.junit.Test;

import sk.qbsw.core.api.client.CApiClient;
import sk.qbsw.core.api.client.http.CHttpApiPutRequest;

/**
 * Creates test call to mscan.qsbw.local (Be sure, that the link is available
 * without proxy settings)
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 * 
 */
public class CHttpPutClientTest
{
	/**
	 * Tests connection without proxy settings
	 */
	@Ignore
	@Test
	public void testNoProxy () throws IOException
	{
		CApiClient<CCallRequestModel, CCallResponseModel> client = new CApiClient<CCallRequestModel, CCallResponseModel>();
		CHttpApiPutRequest put = new CHttpApiPutRequest();
		put.setRepeatCount(2);
		put.setTimeout(10000);

		client.makeCall(put, "http://localhost:8080/RestTest/test", new CCallRequestModel());
	}

	/**
	 * Tests connection with proxy settings
	 */
	@Ignore
	@Test
	public void testWithProxy () throws IOException
	{
		CApiClient<CCallRequestModel, CCallResponseModel> client = new CApiClient<CCallRequestModel, CCallResponseModel>();
		CHttpApiPutRequest put = new CHttpApiPutRequest();
		put.setRepeatCount(2);
		put.setTimeout(10000);
		put.setProxy(new HttpHost("192.168.121.31", 3128, "http"));

		client.makeCall(put, "http://www.sme.sk", new CCallRequestModel());
	}

}
