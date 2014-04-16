package sk.qbsw.core.security.model.jmx;

import sk.qbsw.core.security.model.domain.EHashMethod;

/**
 * The Interface IAuthenticationConfigurator.
 * 
 * @author Tomas Lauro
 * @version 1.8.0
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

	/**
	 * Gets the ldap password hash method.
	 *
	 * @return the ldap password hash method
	 */
	public EHashMethod getLdapPasswordHashMethod ();

	/**
	 * Sets the ldap password hash method.
	 *
	 * @param ldapPasswordHashMethod the new ldap password hash method
	 */
	public void setLdapPasswordHashMethod (EHashMethod ldapPasswordHashMethod);

	/**
	 * Gets the database password hash method.
	 *
	 * @return the database password hash method
	 */
	public EHashMethod getDatabasePasswordHashMethod ();

	/**
	 * Sets the database password hash method.
	 *
	 * @param databasePasswordHashMethod the new database password hash method
	 */
	public void setDatabasePasswordHashMethod (EHashMethod databasePasswordHashMethod);
}
