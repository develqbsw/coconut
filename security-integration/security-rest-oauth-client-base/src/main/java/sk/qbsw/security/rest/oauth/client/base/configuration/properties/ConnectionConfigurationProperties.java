package sk.qbsw.security.rest.oauth.client.base.configuration.properties;

import static sk.qbsw.security.rest.oauth.client.base.configuration.properties.ConnectionConfigurationProperties.CONNECTION_CONFIGURATION_PROPERTIES_PATH;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.client.configuration.ConnectionConfiguration;

/**
 * The security integration REST Oauth client connection configuration properties.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Getter
@Setter
@ConfigurationProperties (CONNECTION_CONFIGURATION_PROPERTIES_PATH)
public class ConnectionConfigurationProperties implements ConnectionConfiguration
{
	public static final String CONNECTION_CONFIGURATION_PROPERTIES_PATH = "coconut.security.integration.rest.oauth.connection-configuration";

	private String proxyHostName;

	private Integer proxyPort;

	@NotNull
	private boolean untrustedSslConnectionAllowed;
}
