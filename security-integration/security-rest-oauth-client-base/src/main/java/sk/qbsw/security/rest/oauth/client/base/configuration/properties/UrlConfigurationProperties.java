package sk.qbsw.security.rest.oauth.client.base.configuration.properties;

import static sk.qbsw.security.rest.oauth.client.base.configuration.properties.UrlConfigurationProperties.URL_CONFIGURATION_PROPERTIES_PATH;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.client.configuration.UrlConfiguration;

/**
 * The security integration REST Oauth client URL configuration properties.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Getter
@Setter
@ConfigurationProperties (URL_CONFIGURATION_PROPERTIES_PATH)
public class UrlConfigurationProperties implements UrlConfiguration
{
	public static final String URL_CONFIGURATION_PROPERTIES_PATH = "coconut.security.integration.rest.oauth.url-configuration";

	@NotNull
	private String scheme;

	@NotNull
	private String hostName;

	private Integer port;

	@NotNull
	private String path;
}
