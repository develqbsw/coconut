package sk.qbsw.core.communication.mail.service;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.communication.mail.dao.IMailDao;
import sk.qbsw.core.communication.mail.exception.CCommunicationException;
import sk.qbsw.core.communication.mail.model.CAttachmentDefinition;
import sk.qbsw.core.communication.mail.model.domain.CMail;
import sk.qbsw.core.communication.mail.model.domain.CQueuedMail;

/**
 * Send mail with job running on the background.
 *
 * @author Tomas Lauro
 * 
 * @version 1.9.1
 * @since 1.9.0
 */
@Service ("mailBackgroundService")
public class CMailBackgroundSender extends AMailService implements IMailService
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CMailBackgroundSender.class);

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.AMailService#setMailDao(sk.qbsw.core.communication.mail.dao.IMailDao)
	 */
	@Autowired
	@Qualifier ("jpaMailDao")
	@Override
	protected void setMailDao (IMailDao mailDao)
	{
		this.mailDao = mailDao;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.AMailService#getMailInstance()
	 */
	@Override
	protected CMail getMailInstance ()
	{
		return new CQueuedMail();
	}

	/**
	 * @deprecated
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
		sendMail(to, subject, body, new CAttachmentDefinition[] {});
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.IMailService#sendMail(java.util.List, java.lang.String, java.lang.String, sk.qbsw.core.communication.mail.model.CAttachmentDefinition[])
	 */
	@Override
	@Transactional
	public void sendMail (List<String> to, String subject, String body, CAttachmentDefinition... attachments)
	{
		if (to != null && !to.isEmpty())
		{
			saveMail(to, null, null, subject, body, attachments);
		}
		else
		{
			throw new InvalidParameterException("Recipient address not set");
		}
	}

	/**
	 * Save mail.
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

			//set date of creation
			mail.setCreated(DateTime.now());

			//send mail
			mailDao.update(mail);
		}
		catch (CCommunicationException e)
		{
			LOGGER.error("Mail sending problem", e);
			throw e;
		}
		catch (CSystemException e)
		{
			LOGGER.error("Mail creating problem", e);
			throw e;
		}
		catch (Exception e)
		{
			LOGGER.error("Mail creating problem", e);
			throw new CSystemException("Mail creating problem", e);
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
}
