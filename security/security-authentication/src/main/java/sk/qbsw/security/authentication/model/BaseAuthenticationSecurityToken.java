package sk.qbsw.security.authentication.model;


/**
 * The abstract authentication token.
 * 
 * @author Tomas Lauro
 * @version 1.13.4
 * @since 1.13.4
 */
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

	@Override
	public Object getCredentials ()
	{
		return this.credentials;
	}

	@Override
	public Object getPrincipal ()
	{
		return this.principal;
	}
}
