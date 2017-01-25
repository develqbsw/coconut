package sk.qbsw.et.mailchimp.client.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sk.qbsw.et.mailchimp.client.model.request.MailchimpAddListMemberRequestBody;
import sk.qbsw.et.mailchimp.client.model.response.MailchimpAddListMemberResponseBody;
import sk.qbsw.et.mailchimp.client.service.MailchimpClient;
import sk.qbsw.et.mailchimp.client.test.configuration.TestSpringConfiguration;

/**
 * The artwork test case.
 * 
 * @author juraj.vrabec
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {TestSpringConfiguration.class})
public class MailchimpClientTestCase
{
	/** The api client. */
	@Autowired
	private MailchimpClient mailchimpClient;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		assertNotNull("Could not find api client", mailchimpClient);
	}

	/**
	 * Adds the list member.
	 */
	@Test
	@Ignore
	public void addListMember ()
	{
		MailchimpAddListMemberRequestBody request = new MailchimpAddListMemberRequestBody();
		request.setEmailAddress("lauro@qbsw.sk");
		request.setStatus("subscribed");

		MailchimpAddListMemberResponseBody response = mailchimpClient.addListMember(request, "8cd0313cb7");

		assertNotNull(response);
	}
}
