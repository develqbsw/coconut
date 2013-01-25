package sk.qbsw.core.api.test.client;

import org.junit.Test;

import sk.qbsw.core.api.client.CApiClient;
import sk.qbsw.core.api.client.http.CHttpApiPostRequest;

public class CHttpGetClientTest
{
	@Test
	public void test ()
	{
		CApiClient<CCallRequestModel, CCallResponseModel> client = new CApiClient<CCallRequestModel, CCallResponseModel>();
		CHttpApiPostRequest post = new CHttpApiPostRequest();
		post.setRepeatCount(2);
		
		client.makeCall(post, "http://www.myxtension.com", new CCallRequestModel());
	}
}
