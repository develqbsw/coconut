package sk.qbsw.core.communication.mail.service;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.communication.mail.dao.IMailDao;
import sk.qbsw.core.communication.mail.model.CAttachmentDefinition;

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
}
