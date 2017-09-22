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

	default String buildUrl (String apiPath)
	{
		StringBuilder urlBuilder = new StringBuilder();

		urlBuilder.append(getScheme()).append("://").append(getHostName()); // base URL
		if (getPort() != null)
		{
			urlBuilder.append(":").append(getPort()); // add port
		}

		urlBuilder.append(getPath() + apiPath); // add api path

		return urlBuilder.toString();
	}
}
