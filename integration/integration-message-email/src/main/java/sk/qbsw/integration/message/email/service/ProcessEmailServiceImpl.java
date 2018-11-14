package sk.qbsw.integration.message.email.service;

import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.integration.message.base.model.domain.MessageStates;
import sk.qbsw.integration.message.email.configuration.properties.EmailStorageConfiguration;
import sk.qbsw.integration.message.email.configuration.properties.ProcessEmailTaskConfiguration;
import sk.qbsw.integration.message.email.model.domain.Email;
import sk.qbsw.integration.message.email.model.domain.EmailAttachment;
import sk.qbsw.integration.message.email.repository.EmailRepository;
import sk.qbsw.integration.message.service.ProcessMessageService;

import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * The process email service implementation.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
@Service
public class ProcessEmailServiceImpl implements ProcessMessageService
{
	private final ProcessEmailTaskConfiguration processEmailTaskConfiguration;

	private final EmailStorageConfiguration emailStorageConfiguration;

	private final EmailRepository emailRepository;

	private final JavaMailSender mailSender;

	private final ResourceLoader resourceLoader;

	/**
	 * Instantiates a new Email service.
	 *
	 * @param processEmailTaskConfiguration the process email task configuration
	 * @param emailStorageConfiguration the email storage configuration
	 * @param emailRepository the email repository
	 * @param mailSender the mail sender
	 * @param resourceLoader the resource loader
	 */
	public ProcessEmailServiceImpl (ProcessEmailTaskConfiguration processEmailTaskConfiguration, //
		EmailStorageConfiguration emailStorageConfiguration, //
		EmailRepository emailRepository, //
		JavaMailSender mailSender, //
		ResourceLoader resourceLoader)
	{
		this.processEmailTaskConfiguration = processEmailTaskConfiguration;
		this.emailStorageConfiguration = emailStorageConfiguration;
		this.emailRepository = emailRepository;
		this.mailSender = mailSender;
		this.resourceLoader = resourceLoader;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void processMessage ()
	{
		List<Email> emails = emailRepository.findByCountAndMaxAttemptCount(processEmailTaskConfiguration.getMaxSendCount(), processEmailTaskConfiguration.getMaxAttemptCount());

		emails.forEach(email -> {
			sendEmail(email, emailStorageConfiguration.getCompleteAttachmentsFileSystemPath());
			emailRepository.save(email);
		});
	}

	private void sendEmail (Email email, String attachmentDirectoryPath)
	{
		MimeMessage message;
		MimeMessageHelper helper;

		try
		{
			// create message and message helper
			message = mailSender.createMimeMessage();
			helper = new MimeMessageHelper(message, true, "UTF-8");

			// set message properties
			helper.setFrom(email.getSender());
			helper.setTo(email.getTo().toArray());

			if (email.getCc() != null)
			{
				helper.setCc(email.getCc().toArray());
			}
			if (email.getBcc() != null)
			{
				helper.setBcc(email.getBcc().toArray());
			}

			helper.setSubject(email.getSubject());
			helper.setText(email.getBody(), email.getHtmlFormat());

			if (email.getAttachments() != null && !email.getAttachments().isEmpty())
			{
				for (EmailAttachment attachment : email.getAttachments())
				{
					helper.addAttachment(attachment.getFileName(), resourceLoader.getResource("file:" + attachmentDirectoryPath + attachment.getFileName()));
				}
			}

			// send message
			mailSender.send(message);

			email.changeState(MessageStates.SENT);
		}
		catch (MailAuthenticationException | MailSendException e)
		{
			email.changeState(MessageStates.NETWORK_ERROR);
		}
		catch (Exception e)
		{
			email.changeState(MessageStates.ERROR);
		}
		finally
		{
			email.setSendAttemptCount(email.getSendAttemptCount() + 1);
		}
	}
}
