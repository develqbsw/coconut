package sk.qbsw.core.api.test.client;

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
	 * @throws Exception 
	 */
	@Ignore
	@Test
	public void testNoProxy () throws Exception
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
	@Ignore
	@Test
	public void testWithProxy () throws Exception
	{
		CApiClient<CCallRequestModel, CCallResponseModel> client = new CApiClient<CCallRequestModel, CCallResponseModel>();
		CHttpApiDeleteRequest delete = new CHttpApiDeleteRequest();
		delete.setRepeatCount(2);
		delete.setTimeout(10000);
		delete.setProxy(new HttpHost("192.168.121.31", 3128, "http"));

		client.makeCall(delete, "http://localhost:8080/RestTest/test", new CCallRequestModel());
	}

}
