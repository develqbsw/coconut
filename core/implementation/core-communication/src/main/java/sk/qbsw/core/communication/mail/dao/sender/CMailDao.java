package sk.qbsw.core.communication.mail.dao.sender;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.communication.mail.dao.IMailDao;
import sk.qbsw.core.communication.mail.exception.CCommunicationException;
import sk.qbsw.core.communication.mail.model.domain.CAttachment;
import sk.qbsw.core.communication.mail.model.domain.CMail;
import sk.qbsw.core.communication.mail.model.domain.EMailState;

/**
 * The dao is sending the mail.
 *
 * @author Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
@Repository(value = "senderMailDao")
public class CMailDao implements IMailDao
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The mail sender. */
	@Autowired
	private JavaMailSender mailSender;

	/** Constructed email message. */
	private MimeMessage message;

	/** The helper. */
	private MimeMessageHelper helper;

	/**
	 * Send the mail through protocol defined in configuration.
	 * 
	 * @param mail The mail to send.
	 */
	@Override
	public CMail update(CMail mail)
	{
		try
		{
			//create message and message helper
			message = mailSender.createMimeMessage();
			helper = new MimeMessageHelper(message, true, "UTF-8");

			//set message properties
			helper.setFrom(mail.getFrom());

			if (mail.getTo() != null)
			{
				helper.setTo(mail.getTo().toArray());
			}
			if (mail.getCc() != null)
			{
				helper.setCc(mail.getCc().toArray());
			}
			if (mail.getBcc() != null)
			{
				helper.setBcc(mail.getBcc().toArray());
			}

			helper.setSubject(mail.getSubject());
			helper.setText(mail.getBody(), true);

			if (mail.getAttachments() != null && mail.getAttachments().size() > 0)
			{
				for (CAttachment attachment : mail.getAttachments())
				{
					if (attachment.getContentType() != null)
					{
						helper.addAttachment(attachment.getFileName(), new ByteArrayResource(attachment.getData()), attachment.getContentType());
					}
					else
					{
						helper.addAttachment(attachment.getFileName(), new ByteArrayResource(attachment.getData()));
					}
				}
			}

			//send message
			mailSender.send(message);

			return mail;
		} catch (MailAuthenticationException e)
		{
			throw new CCommunicationException("Mail sending failed", e);
		} catch (MailSendException e)
		{
			throw new CCommunicationException("Mail sending failed", e);
		} catch (MailException e)
		{
			throw new CCommunicationException("Mail sending failed", e);
		} catch (Throwable e)
		{
			throw new CSystemException("Mail creating failed", e);
		}
	}

	/**
	 * Not implemented.
	 * 
	 * @throws NotImplementedException
	 */
	@Override
	public void remove(CMail object)
	{
		throw new NotImplementedException();
	}

	/**
	 * Not implemented.
	 * 
	 * @throws NotImplementedException
	 */
	@Override
	public void flush()
	{
		throw new NotImplementedException();
	}

	/**
	 * Not implemented.
	 * 
	 * @throws NotImplementedException
	 */
	@Override
	public void clear()
	{
		throw new NotImplementedException();
	}

	/**
	 * Not implemented.
	 * 
	 * @throws NotImplementedException
	 */
	@Override
	public List<CMail> findAllQueued(EMailState state)
	{
		throw new NotImplementedException();
	}

	/**
	 * Not implemented.
	 * 
	 * @throws NotImplementedException
	 */
	@Override
	public List<CMail> findBySubject(String subject)
	{
		throw new NotImplementedException();
	}

	@Override
	public Long create(CMail e) {
		throw new NotImplementedException();
	}

	@Override
	public CMail read(Long id) {
		throw new NotImplementedException();
	}

	@Override
	public void invalidate(CMail e) {
		throw new NotImplementedException();
	}

	@Override
	public void validate(CMail e) {
		throw new NotImplementedException();
	}

	@Override
	public Long getSequenceNextValue(String sequenceName) {
		throw new NotImplementedException();
	}
}
