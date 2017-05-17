package sk.qbsw.integration.mailchimp.client.configuration;

/**
 * The mailchimp client network configuration.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public interface MailchimpClientNetworkConfiguration
{
	/**
	 * Checks if is proxy set.
	 *
	 * @return true, if is proxy set
	 */
	boolean isProxySet ();

	/**
	 * Gets the proxy host.
	 *
	 * @return the proxy host
	 */
	String getProxyHost ();

	/**
	 * Gets the proxy port.
	 *
	 * @return the proxy port
	 */
	Integer getProxyPort ();

	/**
	 * Checks if is untrusted ssl connection allowed.
	 *
	 * @return true, if is untrusted ssl connection allowed
	 */
	boolean isUntrustedSslConnectionAllowed ();
}
