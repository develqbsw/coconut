package sk.qbsw.security.api.authentication.client.configuration;

import java.io.Serializable;

/**
 * Authentication path configuration.
 *
 * @author Roman Farkaš
 * @version 1.18.0
 * @since 1.18.0
 */
public class AuthenticationPathConfiguration implements Serializable
{
	private static final long serialVersionUID = 7222454303635068583L;

	public static final String BASE_PATH = "/security";

	public static final String AUTHENTICATE = BASE_PATH + "/authenticate";

	public static final String REAUTHENTICATE = BASE_PATH + "/reauthenticate";

	public static final String INVALIDATE = BASE_PATH + "/invalidate";


	private AuthenticationPathConfiguration ()
	{
	}
}
