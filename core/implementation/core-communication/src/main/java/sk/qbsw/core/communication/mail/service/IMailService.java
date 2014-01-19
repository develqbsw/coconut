package sk.qbsw.core.communication.mail.service;

import java.io.InputStream;
import java.util.Map;

/**
 * Interface for mail service.
 *
 * @author Jan Sivulka
 * @author Dalibor Rak
 * @version 1.6.0
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
	public abstract void sendEmail (String to, String subject, InputStream template, Map<String, Object> params);

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
}
