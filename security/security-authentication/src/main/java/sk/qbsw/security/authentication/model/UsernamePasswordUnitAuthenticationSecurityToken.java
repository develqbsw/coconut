package sk.qbsw.security.authentication.model;

/**
 * The username, password and unit authentication token.
 * 
 * @author Tomas Lauro
 * @version 1.13.4
 * @since 1.13.4
 */
public class UsernamePasswordUnitAuthenticationSecurityToken extends UsernamePasswordAuthenticationSecurityToken
{
	private static final long serialVersionUID = -996252602003786662L;

	private String unit;

	/**
	 * Instantiates a new c username password unit authentication token.
	 *
	 * @param username the principal
	 * @param password the credentials
	 * @param unit the unit
	 */
	public UsernamePasswordUnitAuthenticationSecurityToken (String username, String password, String unit)
	{
		super(username, password);
		this.unit = unit;
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public String getUnit ()
	{
		return unit;
	}
}
