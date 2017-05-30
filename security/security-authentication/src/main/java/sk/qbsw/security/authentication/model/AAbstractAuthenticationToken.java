package sk.qbsw.security.authentication.model;


/**
 * The abstract authentication token.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.13.4
 */
public abstract class AAbstractAuthenticationToken implements IAuthenticationToken
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8386599362496103338L;

	/** The credentials. */
	private final transient Object credentials;

	/** The principal. */
	private final transient Object principal;

	/**
	 * Instantiates a new a abstract authentication token.
	 *
	 * @param principal the principal
	 * @param credentials the credentials
	 */
	public AAbstractAuthenticationToken (Object principal, Object credentials)
	{
		this.principal = principal;
		this.credentials = credentials;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.model.IAuthenticationToken#getCredentials()
	 */
	@Override
	public Object getCredentials ()
	{
		return this.credentials;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.model.IAuthenticationToken#getPrincipal()
	 */
	@Override
	public Object getPrincipal ()
	{
		return this.principal;
	}
}
