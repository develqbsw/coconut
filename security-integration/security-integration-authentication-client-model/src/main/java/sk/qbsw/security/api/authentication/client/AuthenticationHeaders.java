package sk.qbsw.security.api.authentication.client;

import java.io.Serializable;

/**
 * The common path configuration.
 *
 * @author Roman Farkaš
 * @version 1.18.0
 * @since 1.18.0
 */
public class AuthenticationHeaders implements Serializable
{
	private static final long serialVersionUID = -4378768318941694700L;

	/**
	 * The constant TOKEN_REQUEST_HEADER.
	 */
	public static final String TOKEN_REQUEST_HEADER = "token";

	/**
	 * The constant DEVICE_ID_REQUEST_HEADER.
	 */
	public static final String DEVICE_ID_REQUEST_HEADER = "device-id";

	private AuthenticationHeaders ()
	{
	}
}
