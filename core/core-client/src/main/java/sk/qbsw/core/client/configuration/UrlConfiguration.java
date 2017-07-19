package sk.qbsw.core.client.configuration;

/**
 * The URL configuration.
 * 
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public interface UrlConfiguration
{
	String getScheme ();

	String getHostName ();

	Integer getPort ();

	String getPath ();

	String getProxyHostName ();

	Integer getProxyPort ();

	boolean isUntrustedSslConnectionAllowed ();

	default String buildUrl (String apiPath)
	{
		return new StringBuilder() //
			.append(getScheme()).append("://").append(getHostName()).append(":").append(getPort()) //
			.append(getPath() + apiPath) //
			.toString();
	}
}
