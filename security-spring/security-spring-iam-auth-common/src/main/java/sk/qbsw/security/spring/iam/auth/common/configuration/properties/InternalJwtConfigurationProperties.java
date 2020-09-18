package sk.qbsw.security.spring.iam.auth.common.configuration.properties;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.security.spring.iam.auth.common.configuration.InternalJwtConfiguration;

/**
 * The internal jwt configuration properties.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties ("coconut.security.spring.iam.auth.internal-jwt")
public class InternalJwtConfigurationProperties implements InternalJwtConfiguration
{
	private static final long serialVersionUID = -5781168258402525004L;

	@NotNull
	private String issuer;

	@NotNull
	private String audience;

	@NotNull
	private String subject;

	@NotNull
	private String hmacShaKey;

	@NotNull
	private InternalJwtClaimsConfigurationProperties claims;
}
