package sk.qbsw.security.spring.iam.auth.common.configuration.properties;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.security.spring.iam.auth.common.configuration.InternalJwtClaims;

/**
 * The internal jwt claims.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties ("coconut.security.spring.iam.auth.internal-jwt.claims")
public class InternalJwtClaimsConfigurationProperties implements InternalJwtClaims
{
	private static final long serialVersionUID = -2883991524101034079L;

	@NotNull
	private String uid;

	@NotNull
	private String id;

	@NotNull
	private String login;

	@NotNull
	private String email;

	@NotNull
	private String state;

	@NotNull
	private String roles;

	@NotNull
	private String userId;
}
