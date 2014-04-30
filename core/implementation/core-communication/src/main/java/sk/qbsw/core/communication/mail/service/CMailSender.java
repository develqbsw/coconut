package sk.qbsw.core.communication.mail.service;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.communication.mail.dao.IMailDao;
import sk.qbsw.core.communication.mail.model.CAttachmentDefinition;
import sk.qbsw.core.communication.mail.model.domain.CMail;
import sk.qbsw.core.communication.mail.model.domain.CRecipient;

/**
 * Mail sender.
 *
 * @author Jan Sivulka
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.9.0
 * @since 1.6.0
 */

@Service ("cMailService")
public class CMailSender extends AMailService implements IMailService
{
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(CMailSender.class);

	/** The sender mail dao. */
	@Autowired
	@Qualifier ("senderMailDao")
	private IMailDao mailDao;

	/** The template builder. */
	@Autowired
	private ITemplateBuilder templateBuilder;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.IMailService#sendEmail(java.lang.String, java.lang.String, java.io.InputStream, java.util.Map)
	 */
	@Override
	@Deprecated
	public void sendEmail (String to, String subject, InputStream template, Map<String, Object> params)
	{
		if (to == null || to.length() == 0)
		{
			throw new InvalidParameterException("Recepient address not set");
		}

		try
		{
			//create mail
			CMail mail = new CMail();
			mail.setFrom(senderAdddress);
			mail.setTo(new CRecipient(Arrays.asList(to)));
			mail.setSubject(subject);
			mail.setBody(templateBuilder.buildMailBody(template, params));

			//send mail
			mailDao.save(mail);
		}
		catch (Throwable e)
		{
			logger.error("Mail sending problem", e);
			throw new CSystemException("Mail sending problem", e);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.IMailService#sendMail(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public void sendMail (List<String> to, String subject, String body)
	{
		if (to != null && to.size() > 0)
		{
			sendMail(to, null, null, subject, body, new CAttachmentDefinition[] {});
		}
		else
		{
			throw new InvalidParameterException("Recipient address not set");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.IMailService#sendMail(java.util.List, java.lang.String, java.lang.String, sk.qbsw.core.communication.mail.model.CAttachmentDefinition[])
	 */
	@Override
	public void sendMail (List<String> to, String subject, String body, CAttachmentDefinition... attachments)
	{
		if (to != null && to.size() > 0)
		{
			sendMail(to, null, null, subject, body, attachments);
		}
		else
		{
			throw new InvalidParameterException("Recipient address not set");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.IMailService#setSMTPServer(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void setSMTPServer (String host, Integer port)
	{
		super.setSMTPServer(host, port);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.IMailService#setSenderAddress(java.lang.String)
	 */
	@Override
	public void setSenderAddress (String senderAddress)
	{
		super.setSenderAddress(senderAddress);
	}

	/**
	 * Send mail.
	 *
	 * @param to the to
	 * @param cc the cc
	 * @param bcc the bcc
	 * @param subject the subject
	 * @param body the body
	 * @param attachmentDefinitions the attachment definitions
	 */
	private void sendMail (List<String> to, List<String> cc, List<String> bcc, String subject, String body, CAttachmentDefinition... attachmentDefinitions)
	{
		try
		{
			//create mail
			CMail mail = createMail(to, cc, bcc, subject, body, attachmentDefinitions);

			//send mail
			mailDao.save(mail);
		}
		catch (Throwable e)
		{
			logger.error("Mail sending problem", e);
			throw new CSystemException("Mail sending problem", e);
		}
	}
}
