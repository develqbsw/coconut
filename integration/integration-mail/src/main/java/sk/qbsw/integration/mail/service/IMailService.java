package sk.qbsw.integration.mail.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.integration.mail.exception.CCommunicationException;
import sk.qbsw.integration.mail.model.CAttachmentDefinition;

/**
 * Interface for mail service.
 *
 * @author Jan Sivulka
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.9.1
 * @since 1.6.0
 */
public interface IMailService
{
	/**
	 * Send email.
	 *
	 * @param to the to
	 * @param subject the subject
	 * @param template stream of the template
	 * @param params the parameters of the message
	 */
	@Deprecated
	public void sendEmail (String to, String subject, InputStream template, Map<String, Object> params);

	/**
	 * Send mail.
	 *
	 * @param to the recipient
	 * @param subject the subject
	 * @param body the body
	 * @exception CSystemException mail creating failed - possible data problem
	 * @exception CCommunicationException mail sending failed
	 */
	public void sendMail (List<String> to, String subject, String body);

	/**
	 * Send mail.
	 *
	 * @param to the recipient
	 * @param subject the subject
	 * @param body the body
	 * @param attachments the attachments
	 * @exception CSystemException mail creating failed - possible data problem
	 * @exception CCommunicationException mail sending failed
	 */
	public void sendMail (List<String> to, String subject, String body, CAttachmentDefinition... attachments);

	/**
	 * Sets the smtp server.
	 *
	 * @param host the host
	 * @param port the port
	 */
	public void setSMTPServer (String host, Integer port);

	/**
	 * Sets the sender address.
	 *
	 * @param senderAddress the new sender address
	 */
	public void setSenderAddress (String senderAddress);

	/**
	 * Checks if is archive - if true the mails which are sent stays in DB, if false they are removed.
	 *
	 * @return true, if is archive
	 */
	public boolean isArchive ();

	/**
	 * The flag archive - if true the mails which are sent stays in DB, if false they are removed.
	 *
	 * @param archive the new archive
	 */
	public void setArchive (boolean archive);
}
