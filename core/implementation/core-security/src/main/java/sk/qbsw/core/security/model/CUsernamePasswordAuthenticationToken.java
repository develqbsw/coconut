package sk.qbsw.core.security.model;

/**
 * The username and password authentication token.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.13.4
 */
public class CUsernamePasswordAuthenticationToken extends AAbstractAuthenticationToken
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1170478881221601581L;

	/**
	 * Instantiates a new c username password authentication token.
	 *
	 * @param principal the principal
	 * @param credentials the credentials
	 */
	public CUsernamePasswordAuthenticationToken (String username, String password)
	{
		super(username, password);
	}
}
