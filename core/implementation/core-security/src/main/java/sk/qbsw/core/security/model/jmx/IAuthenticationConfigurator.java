package sk.qbsw.core.security.model.jmx;

/**
 * The Interface IAuthenticationConfigurator.
 * 
 * @author Tomas Lauro
 * @version 1.7.2
 * @since 1.7.2
 */
public interface IAuthenticationConfigurator
{
	/**
	 * Gets the password pattern.
	 *
	 * @return the password pattern
	 */
	public String getPasswordPattern ();

	/**
	 * Sets the password pattern.
	 *
	 * @param passwordPattern the new password pattern
	 */
	public void setPasswordPattern (String passwordPattern);
}
