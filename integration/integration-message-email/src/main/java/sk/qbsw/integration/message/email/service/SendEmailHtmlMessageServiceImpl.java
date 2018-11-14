package sk.qbsw.integration.message.email.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.integration.message.email.model.EmailHtmlBodyData;
import sk.qbsw.integration.message.email.model.EmailRecipientData;
import sk.qbsw.integration.message.email.model.domain.Email;
import sk.qbsw.integration.message.email.repository.EmailRepository;
import sk.qbsw.integration.message.exception.InvalidMessageDataException;
import sk.qbsw.integration.message.model.AttachmentData;
import sk.qbsw.integration.message.service.SendMessageService;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * The send email HTML message service.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class SendEmailHtmlMessageServiceImpl extends SendEmailMessageServiceBase implements SendMessageService<Email, EmailRecipientData, EmailHtmlBodyData, AttachmentData>
{
	private final EmailTemplateBuilder emailTemplateBuilder;

	private final EmailRepository emailRepository;

	/**
	 * Instantiates a new Send email html message service.
	 *
	 * @param emailTemplateBuilder the email template builder
	 * @param emailRepository the email repository
	 */
	public SendEmailHtmlMessageServiceImpl (EmailTemplateBuilder emailTemplateBuilder, EmailRepository emailRepository)
	{
		this.emailTemplateBuilder = emailTemplateBuilder;
		this.emailRepository = emailRepository;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Email sendMessage (EmailRecipientData recipient, EmailHtmlBodyData htmlBody, AttachmentData... attachments) throws InvalidMessageDataException
	{
		String body = createBodyFromTemplate(htmlBody.getTemplate(), htmlBody.getParams());

		return emailRepository.save(createEmail(recipient, body, true, attachments));
	}

	private String createBodyFromTemplate (InputStream template, Map<String, Object> params) throws InvalidMessageDataException
	{
		try
		{
			return emailTemplateBuilder.buildBody(template, params);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new InvalidMessageDataException("Unsupported encoding exception", e);
		}
	}
}
