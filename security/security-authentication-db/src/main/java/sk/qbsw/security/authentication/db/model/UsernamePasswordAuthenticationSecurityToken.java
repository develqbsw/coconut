package sk.qbsw.security.authentication.db.model;

/**
 * The username and password authentication token.
 *
 * @author Tomas Lauro
 * @version 1.13.4
 * @since 1.13.4
 */
public class UsernamePasswordAuthenticationSecurityToken extends BaseAuthenticationSecurityToken
{
	private static final long serialVersionUID = 1170478881221601581L;

	/**
	 * Instantiates a new c username password authentication token.
	 *
	 * @param username the username
	 * @param password the password
	 */
	public UsernamePasswordAuthenticationSecurityToken (String username, String password)
	{
		super(username, password);
	}
}
