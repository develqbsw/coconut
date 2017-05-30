package sk.qbsw.security.authentication.model;

/**
 * The username, password and unit authentication token.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.13.4
 */
public class CUsernamePasswordUnitAuthenticationToken extends CUsernamePasswordAuthenticationToken
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -996252602003786662L;

	/** The unit. */
	private String unit;

	/**
	 * Instantiates a new c username password unit authentication token.
	 *
	 * @param username the principal
	 * @param password the credentials
	 * @param unit the unit
	 */
	public CUsernamePasswordUnitAuthenticationToken (String username, String password, String unit)
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
