package sk.qbsw.integration.message.email.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.integration.message.email.model.EmailHtmlBodyData;
import sk.qbsw.integration.message.email.model.EmailRecipientData;
import sk.qbsw.integration.message.email.model.domain.Email;
import sk.qbsw.integration.message.email.repository.EmailRepository;
import sk.qbsw.integration.message.email.service.EmailTemplateBuilder;
import sk.qbsw.integration.message.email.service.EmailTemplateBuilderImpl;
import sk.qbsw.integration.message.email.service.SendEmailHtmlMessageServiceImpl;
import sk.qbsw.integration.message.model.AttachmentData;
import sk.qbsw.integration.message.service.SendMessageService;

/**
 * The email HTML message configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class EmailHtmlMessageConfiguration extends EmailMessageConfigurationBase
{
	@Bean
	public EmailTemplateBuilder emailTemplateBuilder ()
	{
		return new EmailTemplateBuilderImpl();
	}

	@Bean
	public SendMessageService<Email, EmailRecipientData, EmailHtmlBodyData, AttachmentData> sendEmailHtmlMessageService (EmailRepository emailRepository, EmailTemplateBuilder emailTemplateBuilder)
	{
		return new SendEmailHtmlMessageServiceImpl(emailTemplateBuilder, emailRepository);
	}
}
