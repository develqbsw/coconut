package sk.qbsw.core.communication.mail.service;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.communication.mail.dao.IMailDao;
import sk.qbsw.core.communication.mail.model.CAttachmentDefinition;
import sk.qbsw.core.communication.mail.model.domain.CMail;

/**
 * Send mail with job running on the background.
 *
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
@Service ("mailBackgroundService")
public class CMailBackgroundSender extends AMailService implements IMailService
{
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(CMailBackgroundSender.class);

	/** The sender mail dao. */
	@Autowired
	@Qualifier ("jpaMailDao")
	private IMailDao mailDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.IMailService#sendEmail(java.lang.String, java.lang.String, java.io.InputStream, java.util.Map)
	 */
	@Override
	@Deprecated
	public void sendEmail (String to, String subject, InputStream template, Map<String, Object> params)
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.IMailService#sendMail(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void sendMail (List<String> to, String subject, String body)
	{
		if (to != null && to.size() > 0)
		{
			saveMail(to, null, null, subject, body, new CAttachmentDefinition[] {});
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
	@Transactional
	public void sendMail (List<String> to, String subject, String body, CAttachmentDefinition... attachments)
	{
		if (to != null && to.size() > 0)
		{
			saveMail(to, null, null, subject, body, attachments);
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
	 * Save mail to database.
	 *
	 * @param to the recipient
	 * @param cc the cc recipient
	 * @param bcc the bcc recipient
	 * @param subject the subject
	 * @param body the body
	 * @param attachmentDefinitions the attachment definitions
	 */
	private void saveMail (List<String> to, List<String> cc, List<String> bcc, String subject, String body, CAttachmentDefinition... attachmentDefinitions)
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
