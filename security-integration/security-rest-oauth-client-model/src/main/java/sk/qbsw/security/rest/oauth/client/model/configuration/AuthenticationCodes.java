package sk.qbsw.security.rest.oauth.client.model.configuration;

import java.io.Serializable;

/**
 * The common codes.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.18.0
 */
public class AuthenticationCodes implements Serializable
{
	private static final long serialVersionUID = -4378768318941694700L;

	/**
	 * The constant APPLICATION_HTTP_ERROR_CODE.
	 */
	public static final int APPLICATION_HTTP_ERROR_CODE = 555;

	private AuthenticationCodes ()
	{
	}
}
