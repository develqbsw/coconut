package sk.qbsw.core.security.oauth.model.jmx;

/**
 * The oauth configurator.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.1
 */
public interface IOauthConfigurator
{
	/**
	 * Checks if is ip ignored.
	 *
	 * @return true, if is ip ignored
	 */
	boolean isIpIgnored ();

	/**
	 * Sets the ip ignored.
	 *
	 * @param ipIgnored the new ip ignored
	 */
	void setIpIgnored (boolean ipIgnored);
}
