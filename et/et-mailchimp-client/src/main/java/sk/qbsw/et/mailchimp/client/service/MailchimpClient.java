package sk.qbsw.et.mailchimp.client.service;

import sk.qbsw.et.mailchimp.client.exception.MailchimpException;
import sk.qbsw.et.mailchimp.client.exception.MailchimpTooManyRequestsException;
import sk.qbsw.et.mailchimp.client.model.request.MailchimpAddListMemberRequestBody;
import sk.qbsw.et.mailchimp.client.model.response.MailchimpAddListMemberResponseBody;

/**
 * The internal client.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public interface MailchimpClient
{
	/**
	 * Adds the list member.
	 *
	 * @param request the request
	 * @param listId the list id
	 * @return the mailchimp add list member response body
	 * @throws MailchimpException the generic mailchimp exception
	 * @throws MailchimpTooManyRequestsException the too many requests
	 */
	MailchimpAddListMemberResponseBody addListMember (MailchimpAddListMemberRequestBody request, String listId);
}
