package sk.qbsw.et.mailchimp.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import sk.qbsw.et.mailchimp.client.communication.UrlProvider;
import sk.qbsw.et.mailchimp.client.configuration.MailchimpPathConfiguration;
import sk.qbsw.et.mailchimp.client.model.request.MailchimpAddListMemberRequestBody;
import sk.qbsw.et.mailchimp.client.model.response.MailchimpAddListMemberResponseBody;

/**
 * The API client.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
@Component
public class MailchimpClientImpl implements MailchimpClient
{
	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;

	/** The configuration. */
	@Autowired
	private UrlProvider mailchimpUrlProvider;

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.mailchimp.client.service.MailchimpApiClient#addListMember(sk.qbsw.lomnicagallery.mailchimp.client.model.request.MailchimpAddListMemberRequestBody, java.lang.String)
	 */
	@Override
	public MailchimpAddListMemberResponseBody addListMember (MailchimpAddListMemberRequestBody request, String listId)
	{
		return restTemplate.postForObject(mailchimpUrlProvider.createAddress(MailchimpPathConfiguration.LIST_MEMBERS), request, MailchimpAddListMemberResponseBody.class, listId);
	}
}
