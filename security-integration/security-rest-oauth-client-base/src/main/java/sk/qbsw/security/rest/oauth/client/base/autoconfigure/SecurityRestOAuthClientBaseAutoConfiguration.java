package sk.qbsw.security.rest.oauth.client.base.autoconfigure;

import static sk.qbsw.security.rest.oauth.client.base.configuration.properties.ConnectionConfigurationProperties.CONNECTION_CONFIGURATION_PROPERTIES_PATH;
import static sk.qbsw.security.rest.oauth.client.base.configuration.properties.UrlConfigurationProperties.URL_CONFIGURATION_PROPERTIES_PATH;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import sk.qbsw.core.client.configuration.ConnectionConfiguration;
import sk.qbsw.core.client.configuration.UrlConfiguration;
import sk.qbsw.core.client.factory.BaseRestTemplateFactory;
import sk.qbsw.security.rest.oauth.client.base.configuration.properties.ConnectionConfigurationProperties;
import sk.qbsw.security.rest.oauth.client.base.configuration.properties.UrlConfigurationProperties;

/**
 * The security integration REST OAuth client base auto configuration.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
public class SecurityRestOAuthClientBaseAutoConfiguration
{
	private static final String URL_CONFIGURATION_BEAN_NAME = "restOauthRestUrlConfiguration";
	private static final String CONNECTION_CONFIGURATION_BEAN_NAME = "restOauthRestConnectionConfiguration";
	private static final String REST_TEMPLATE_BEAN_NAME = "restOAuthRestTemplate";

	@Bean
	@ConditionalOnMissingBean (name = URL_CONFIGURATION_BEAN_NAME)
	@ConditionalOnProperty (name = URL_CONFIGURATION_PROPERTIES_PATH)
	public UrlConfiguration restOauthRestUrlConfiguration ()
	{
		return new UrlConfigurationProperties();
	}

	@Bean
	@ConditionalOnMissingBean (name = CONNECTION_CONFIGURATION_BEAN_NAME)
	@ConditionalOnProperty (name = CONNECTION_CONFIGURATION_PROPERTIES_PATH)
	public ConnectionConfiguration restOauthRestConnectionConfiguration ()
	{
		return new ConnectionConfigurationProperties();
	}

	@Bean
	@ConditionalOnMissingBean (name = REST_TEMPLATE_BEAN_NAME)
	public FactoryBean<RestTemplate> restOAuthRestTemplate (ConnectionConfiguration restOauthRestConnectionConfiguration)
	{
		return new BaseRestTemplateFactory(restOauthRestConnectionConfiguration)
		{
		};
	}
}
