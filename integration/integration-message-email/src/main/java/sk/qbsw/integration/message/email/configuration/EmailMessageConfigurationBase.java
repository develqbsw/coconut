package sk.qbsw.integration.message.email.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import sk.qbsw.core.persistence.dao.support.CFetchCapableQueryDslJpaRepositoryFactoryBean;
import sk.qbsw.integration.message.email.configuration.properties.EmailNetworkConfiguration;
import sk.qbsw.integration.message.email.configuration.properties.EmailStorageConfiguration;
import sk.qbsw.integration.message.email.configuration.properties.ProcessEmailTaskConfiguration;
import sk.qbsw.integration.message.email.repository.EmailRepository;
import sk.qbsw.integration.message.email.service.ProcessEmailServiceImpl;
import sk.qbsw.integration.message.email.task.ProcessEmailTask;
import sk.qbsw.integration.message.service.ProcessMessageService;

/**
 * The email message configuration base.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@EnableJpaRepositories (basePackages = { //
	"sk.qbsw.integration.message.email.repository"}, //
	repositoryFactoryBeanClass = CFetchCapableQueryDslJpaRepositoryFactoryBean.class)
@EnableTransactionManagement
public abstract class EmailMessageConfigurationBase
{
	@Bean
	public ProcessEmailTask processEmailTask (ProcessMessageService processMessageService)
	{
		return new ProcessEmailTask(processMessageService);
	}

	@Bean
	public ProcessEmailServiceImpl processMessageService (ProcessEmailTaskConfiguration processEmailTaskConfiguration, EmailStorageConfiguration emailStorageConfiguration, EmailRepository emailRepository, JavaMailSender mailSender, ResourceLoader resourceLoader)
	{
		return new ProcessEmailServiceImpl(processEmailTaskConfiguration, emailStorageConfiguration, emailRepository, mailSender, resourceLoader);
	}

	@Bean
	public JavaMailSender mailSender (EmailNetworkConfiguration mailConfiguration)
	{
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost(mailConfiguration.getHost());
		javaMailSender.setPort(mailConfiguration.getPort());

		javaMailSender.setUsername(mailConfiguration.getUsername());
		javaMailSender.setPassword(mailConfiguration.getPassword());

		javaMailSender.setJavaMailProperties(getMailProperties(mailConfiguration));

		return javaMailSender;
	}

	private Properties getMailProperties (EmailNetworkConfiguration mailConfiguration)
	{
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", mailConfiguration.getTransportProtocol());
		properties.setProperty("mail.smtp.auth", mailConfiguration.getSmtpAuth());
		properties.setProperty("mail.smtp.starttls.enable", mailConfiguration.getSmtpStarttlsEnable());
		properties.setProperty("mail.debug", mailConfiguration.getDebug());
		properties.setProperty("mail.smtp.connectiontimeout", mailConfiguration.getSmtpConnectiontimeout());
		properties.setProperty("mail.smtp.sendpartial", mailConfiguration.getSmtpSendpartial());
		properties.setProperty("mail.smtp.userset", mailConfiguration.getSmtpUserset());
		properties.setProperty("mail.mime.charset", mailConfiguration.getMimeCharset());

		return properties;
	}
}
