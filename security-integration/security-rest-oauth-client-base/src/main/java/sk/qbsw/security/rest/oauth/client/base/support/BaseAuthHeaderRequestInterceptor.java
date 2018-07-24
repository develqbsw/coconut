package sk.qbsw.security.rest.oauth.client.base.support;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import sk.qbsw.security.rest.oauth.client.model.configuration.AuthenticationHeaders;
import sk.qbsw.security.rest.oauth.client.model.configuration.AuthenticationPaths;

import java.io.IOException;

/**
 * The base authentication header request interceptor.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
public abstract class BaseAuthHeaderRequestInterceptor implements ClientHttpRequestInterceptor
{
	/**
	 * Gets device id.
	 *
	 * @return the device id
	 */
	public abstract String getDeviceId ();

	/**
	 * Gets security token.
	 *
	 * @return the security token
	 */
	public abstract String getSecurityToken ();

	@Override
	public ClientHttpResponse intercept (HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException
	{
		if (request.getHeaders().getContentType() == null)
		{
			request.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
		}

		// set always device id
		if (getDeviceId() != null)
		{
			request.getHeaders().set(AuthenticationHeaders.DEVICE_ID_REQUEST_HEADER, getDeviceId());
		}

		if (getSecurityToken() != null)
		{
			if (!request.getURI().getPath().contains(AuthenticationPaths.SECURITY_AUTHENTICATE) && !request.getURI().getPath().contains(AuthenticationPaths.SECURITY_REAUTHENTICATE) && !request.getURI().getPath().contains(AuthenticationPaths.SECURITY_INVALIDATE) && !request.getURI().getPath().contains(AuthenticationPaths.SECURITY_VERIFY))
			{
				request.getHeaders().set(AuthenticationHeaders.TOKEN_REQUEST_HEADER, getSecurityToken());
			}
		}

		return execution.execute(request, body);
	}
}
