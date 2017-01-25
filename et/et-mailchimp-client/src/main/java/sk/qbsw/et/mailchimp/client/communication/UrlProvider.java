package sk.qbsw.et.mailchimp.client.communication;

/**
 * The internal client configurator.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public interface UrlProvider
{
	/**
	 * Gets the scheme.
	 *
	 * @return the scheme
	 */
	String getScheme ();

	/**
	 * Gets the dc.
	 *
	 * @return the dc
	 */
	String getDc ();

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	String getHost ();

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	Integer getPort ();

	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	String getRoot ();

	/**
	 * Gets the address.
	 *
	 * @param path the path
	 * @return the address
	 */
	String createAddress (String path);
}
