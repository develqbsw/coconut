package sk.qbsw.security.authentication.model;

/**
 * The username and password authentication token.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.13.4
 */
public class CustomUsernamePasswordAuthenticationToken extends CustomAbstractAuthenticationToken
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1170478881221601581L;

	/**
	 * Instantiates a new c username password authentication token.
	 *
	 * @param principal the principal
	 * @param credentials the credentials
	 */
	public CustomUsernamePasswordAuthenticationToken (String username, String password)
	{
		super(username, password);
	}
}
