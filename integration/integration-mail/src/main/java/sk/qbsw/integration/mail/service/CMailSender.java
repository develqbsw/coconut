package sk.qbsw.integration.mail.service;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.integration.mail.dao.IMailDao;
import sk.qbsw.integration.mail.exception.CCommunicationException;
import sk.qbsw.integration.mail.model.CAttachmentDefinition;
import sk.qbsw.integration.mail.model.domain.CMail;
import sk.qbsw.integration.mail.model.domain.CRecipient;
import sk.qbsw.integration.mail.model.domain.EMailState;

/**
 * Mail sender.
 *
 * @author Jan Sivulka
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.9.1
 * @since 1.6.0
 */

@Service ("cMailService")
public class CMailSender extends AMailService implements IMailService
{

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CMailSender.class);

	/** The template builder. */
	@Autowired
	private ITemplateBuilder templateBuilder;

	/** The jpa mail dao. */
	@Autowired
	@Qualifier ("jpaMailDao")
	private IMailDao jpaMailDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.integration.mail.service.AMailService#setMailDao(sk.qbsw.integration.mail.dao.IMailDao)
	 */
	@Autowired
	@Qualifier ("senderMailDao")
	@Override
	protected void setMailDao (IMailDao mailDao)
	{
		this.mailDao = mailDao;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.integration.mail.service.AMailService#getMailInstance()
	 */
	@Override
	protected CMail getMailInstance ()
	{
		return new CMail();
	}

	/** (non-Javadoc)
	 * @deprecated
	 * @see sk.qbsw.integration.mail.service.IMailService#sendEmail(java.lang.String, java.lang.String, java.io.InputStream, java.util.Map)
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
			mailDao.update(mail);
		}
		catch (Exception e)
		{
			LOGGER.error("Mail sending problem", e);
			throw new CSystemException("Mail sending problem", e);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.integration.mail.service.IMailService#sendMail(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public void sendMail (List<String> to, String subject, String body)
	{
		if (to != null && !to.isEmpty())
		{
			saveMail(to, null, null, subject, body, new CAttachmentDefinition[] {});
		}
		else
		{
			throw new InvalidParameterException("Recipient address not set");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.integration.mail.service.IMailService#sendMail(java.util.List, java.lang.String, java.lang.String, sk.qbsw.integration.mail.model.CAttachmentDefinition[])
	 */
	@Override
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
		CMail mail = null;

		try
		{
			//create mail
			mail = createMail(to, cc, bcc, subject, body, attachmentDefinitions);

			//set date of creation
			mail.setCreated(OffsetDateTime.now());

			//send mail
			mailDao.update(mail);
			mail.setState(EMailState.SENT);
		}
		catch (CCommunicationException e)
		{
			LOGGER.error("Mail sending problem", e);
			if (mail != null)
			{
				mail.setState(EMailState.COMMUNICATION_ERROR);
			}
			throw e;
		}
		catch (CSystemException e)
		{
			LOGGER.error("Mail creating problem", e);
			if (mail != null)
			{
				mail.setState(EMailState.DATA_ERROR); //the mail is probably null
			}
			throw e;
		}
		catch (Exception e)
		{
			LOGGER.error("Mail creating problem", e);
			if (mail != null)
			{
				mail.setState(EMailState.DATA_ERROR); //the mail is probably null
			}
			throw new CSystemException("Mail creating problem", e);
		}
		finally
		{
			if (archive && mail != null)
			{
				jpaMailDao.update(mail);
			}
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.integration.mail.service.IMailService#setSMTPServer(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void setSMTPServer (String host, Integer port)
	{
		super.setSMTPServer(host, port);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.integration.mail.service.IMailService#setSenderAddress(java.lang.String)
	 */
	@Override
	public void setSenderAddress (String senderAddress)
	{
		super.setSenderAddress(senderAddress);
	}
}
