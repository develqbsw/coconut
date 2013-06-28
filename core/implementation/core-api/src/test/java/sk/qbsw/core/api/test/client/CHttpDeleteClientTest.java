package sk.qbsw.core.api.test.client;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.junit.Ignore;
import org.junit.Test;

import sk.qbsw.core.api.client.CApiClient;
import sk.qbsw.core.api.client.http.CHttpApiDeleteRequest;

/**
 * Creates test call to localhost RestTest application (Be sure, that the link is available
 * without proxy settings)
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 * 
 */
public class CHttpDeleteClientTest
{
	/**
	 * Tests the connection without proxy settings
	 */
	@Ignore
	@Test
	public void testNoProxy () throws IOException
	{
		CApiClient<CCallRequestModel, CCallResponseModel> client = new CApiClient<CCallRequestModel, CCallResponseModel>();
		CHttpApiDeleteRequest delete = new CHttpApiDeleteRequest();
		delete.setRepeatCount(2);
		delete.setTimeout(10000);

		client.makeCall(delete, "http://localhost:8080/RestTest/test", new CCallRequestModel());
	}

	/**
	 * Tests the connection with proxy settings
	 */
	@Test
	public void testWithProxy () throws IOException
	{
		CApiClient<CCallRequestModel, CCallResponseModel> client = new CApiClient<CCallRequestModel, CCallResponseModel>();
		CHttpApiDeleteRequest delete = new CHttpApiDeleteRequest();
		delete.setRepeatCount(2);
		delete.setTimeout(10000);
		delete.setProxy(new HttpHost("192.168.121.31", 3128, "http"));

		System.out.println(client.makeCall(delete, "http://localhost:8080/RestTest/test", new CCallRequestModel()));
	}

}
