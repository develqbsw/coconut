package sk.qbsw.core.client.configuration;

/**
 * The connection configuration.
 * 
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public interface ConnectionConfiguration
{
	String getProxyHostName ();

	Integer getProxyPort ();

	boolean isUntrustedSslConnectionAllowed ();
}
