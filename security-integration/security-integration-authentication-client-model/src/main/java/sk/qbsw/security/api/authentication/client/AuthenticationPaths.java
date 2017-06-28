package sk.qbsw.security.api.authentication.client;

import java.io.Serializable;

/**
 * Authentication path configuration.
 *
 * @author Roman Farkaš
 * @version 1.18.0
 * @since 1.18.0
 */
public class AuthenticationPaths implements Serializable
{
	private static final long serialVersionUID = 7222454303635068583L;

	/**
	 * The constant BASE_PATH.
	 */
	public static final String BASE_PATH = "/security";

	/**
	 * The constant AUTHENTICATE.
	 */
	public static final String AUTHENTICATE = "/authenticate";

	/**
	 * The constant REAUTHENTICATE.
	 */
	public static final String REAUTHENTICATE = "/reauthenticate";

	/**
	 * The constant INVALIDATE.
	 */
	public static final String INVALIDATE = "/invalidate";

	/**
	 * The constant VERIFY.
	 */
	public static final String VERIFY = "/verify";

	private AuthenticationPaths ()
	{
	}
}
