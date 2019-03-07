package sk.qbsw.security.spring.common.configuration;

import lombok.Getter;

/**
 * The default auth header configuration.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
@Getter
public class DefaultAuthHeaderConfiguration implements AuthHeaderConfiguration
{
	private static final long serialVersionUID = -3812238323243640985L;

	private String requestSecurityHeaderTokenName = "token";
}
