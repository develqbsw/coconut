package sk.qbsw.integration.mailchimp.client.test.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * The test configuration.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan ({"sk.qbsw.integration.mailchimp.client"})
public class TestSpringConfiguration
{
	/**
	 * Validator.
	 *
	 * @return the local validator factory bean
	 */
	@Bean
	public LocalValidatorFactoryBean validator ()
	{
		return new LocalValidatorFactoryBean();
	}
}
