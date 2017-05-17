package sk.qbsw.integration.mailchimp.client.model;

import java.io.Serializable;

import sk.qbsw.integration.mailchimp.client.exception.MailchimpException;

/**
 * The base entity.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 * 
 */
public abstract class BaseEntity implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1450598467374059743L;

	/**
	 * Validate.
	 */
	public void validate () throws MailchimpException
	{
		//nothing to validate
	}
}
