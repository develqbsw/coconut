package sk.qbsw.security.rest.oauth.client.model.configuration;

import java.io.Serializable;

/**
 * Authentication path configuration.
 *
 * @author Roman Farkaš
 * @version 2.0.0
 * @since 1.18.0
 */
public class AuthenticationPaths implements Serializable
{
	private static final long serialVersionUID = 7222454303635068583L;

	/**
	 * The constant BASE_PATH.
	 */
	public static final String BASE_PATH = "${coconut.security.path.base}";

	/**
	 * The constant SECURITY_AUTHENTICATE.
	 */
	public static final String SECURITY_AUTHENTICATE = "/security/authenticate";

	/**
	 * The constant SECURITY_REAUTHENTICATE.
	 */
	public static final String SECURITY_REAUTHENTICATE = "/security/reauthenticate";

	/**
	 * The constant SECURITY_INVALIDATE.
	 */
	public static final String SECURITY_INVALIDATE = "/security/invalidate";

	/**
	 * The constant SECURITY_VERIFY.
	 */
	public static final String SECURITY_VERIFY = "/security/verify";

	private AuthenticationPaths ()
	{
	}
}
