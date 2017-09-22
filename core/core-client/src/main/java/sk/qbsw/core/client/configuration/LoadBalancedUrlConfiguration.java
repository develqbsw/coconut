package sk.qbsw.core.client.configuration;

/**
 * The URL configuration.
 * 
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public interface LoadBalancedUrlConfiguration
{
	String getScheme ();

	String getPath ();

	default String buildUrl (String serviceName, String apiPath)
	{
		StringBuilder urlBuilder = new StringBuilder();

		return urlBuilder.append(getScheme()).append("://").append(serviceName).append(getPath() + apiPath).toString();
	}
}
