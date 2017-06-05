package sk.qbsw.security.api.authentication.client.configuration;

import java.io.Serializable;

/**
 * The common path configuration.
 *
 * @author Roman Farkaš
 * @version 1.18.0
 * @since 1.18.0
 */
public class AuthenticationApiConfiguration implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4378768318941694700L;

	public static final int APPLICATION_HTTP_ERROR_CODE = 555;

	public static final String TOKEN_REQUEST_HEADER = "token";

	public static final String DEVICE_ID_REQUEST_HEADER = "device-id";

	private AuthenticationApiConfiguration ()
	{
	}
}
