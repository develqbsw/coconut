package sk.qbsw.integration.message.email.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.integration.message.email.model.EmailPlainTextBodyData;
import sk.qbsw.integration.message.email.model.EmailRecipientData;
import sk.qbsw.integration.message.email.model.domain.Email;
import sk.qbsw.integration.message.email.repository.EmailRepository;
import sk.qbsw.integration.message.model.AttachmentData;
import sk.qbsw.integration.message.service.SendMessageService;

/**
 * The send email plain text message service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class SendEmailPlainTextMessageServiceImpl extends SendEmailMessageServiceBase implements SendMessageService<Email, EmailRecipientData, EmailPlainTextBodyData, AttachmentData>
{
	private final EmailRepository emailRepository;

	/**
	 * Instantiates a new Send email plain text message service.
	 *
	 * @param emailRepository the email repository
	 */
	public SendEmailPlainTextMessageServiceImpl (EmailRepository emailRepository)
	{
		this.emailRepository = emailRepository;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Email sendMessage (EmailRecipientData recipient, EmailPlainTextBodyData plainTextBody, AttachmentData... attachments)
	{
		return emailRepository.save(createEmail(recipient, plainTextBody.getBody(), false, attachments));
	}
}
