package sk.qbsw.security.spring.iam.auth.common.configuration;

import lombok.Getter;
import sk.qbsw.security.spring.common.configuration.AuthHeaderConfiguration;

/**
 * The default IAM auth header configuration.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
@Getter
public class DefaultIAMAuthHeaderConfiguration implements AuthHeaderConfiguration
{
	private static final long serialVersionUID = -3812238323243640985L;

	private String requestSecurityHeaderTokenName = "authorization-iam";
}
