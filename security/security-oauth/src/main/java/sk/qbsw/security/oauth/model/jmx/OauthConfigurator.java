package sk.qbsw.security.oauth.model.jmx;

/**
 * The oauth configurator.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.1
 */
public interface OauthConfigurator
{
	/**
	 * Checks if is ip ignored when checking master token.
	 *
	 * @return true, if is ip ignored
	 */
	boolean isIpIgnored ();

	/**
	 * Sets the ip ignored when checking master token.
	 *
	 * @param ipIgnored the new ip ignored
	 */
	void setIpIgnored (boolean ipIgnored);
	/**
	 * Checks if is ip ignored when checking auth token.
	 *
	 * @return true, if is ip ignored
	 */
	boolean isAuthIpIgnored ();
	
	/**
	 * Sets the ip ignored when checking auth token.
	 *
	 * @param ipIgnored the new ip ignored
	 */
	void setAuthIpIgnored (boolean ipIgnored);
}
