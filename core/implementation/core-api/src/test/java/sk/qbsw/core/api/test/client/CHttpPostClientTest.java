package sk.qbsw.core.api.test.client;

import org.junit.Test;

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
	@Test
	public void test() 
	{
		CApiClient<CCallRequestModel, CCallResponseModel> client = new CApiClient<CCallRequestModel, CCallResponseModel>();
		CHttpApiPostRequest post = new CHttpApiPostRequest();
		post.setRepeatCount(2);

		client.makeCall(post, "http://mscan.qbsw.local/admin/index.html", new CCallRequestModel());
	}
}
