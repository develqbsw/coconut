package sk.qbsw.security.authentication.db.model;

import lombok.Getter;

/**
 * The abstract authentication token.
 * 
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.4
 */
@Getter
public abstract class BaseAuthenticationSecurityToken implements AuthenticationSecurityToken
{
	private static final long serialVersionUID = -8386599362496103338L;

	private final transient Object credentials;

	private final transient Object principal;

	/**
	 * Instantiates a new a abstract authentication token.
	 *
	 * @param principal the principal
	 * @param credentials the credentials
	 */
	public BaseAuthenticationSecurityToken (Object principal, Object credentials)
	{
		this.principal = principal;
		this.credentials = credentials;
	}
}
