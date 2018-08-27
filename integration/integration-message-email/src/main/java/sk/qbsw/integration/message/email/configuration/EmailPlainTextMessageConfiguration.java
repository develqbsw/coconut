package sk.qbsw.integration.message.email.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.integration.message.email.model.EmailPlainTextBodyData;
import sk.qbsw.integration.message.email.model.EmailRecipientData;
import sk.qbsw.integration.message.email.model.domain.Email;
import sk.qbsw.integration.message.email.repository.EmailRepository;
import sk.qbsw.integration.message.email.service.SendEmailPlainTextMessageServiceImpl;
import sk.qbsw.integration.message.model.AttachmentData;
import sk.qbsw.integration.message.service.SendMessageService;

/**
 * The email plaint text message configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class EmailPlainTextMessageConfiguration extends EmailMessageConfigurationBase
{
	@Bean
	public SendMessageService<Email, EmailRecipientData, EmailPlainTextBodyData, AttachmentData> sendEmailPlainTextMessageService (EmailRepository emailRepository)
	{
		return new SendEmailPlainTextMessageServiceImpl(emailRepository);
	}
}
