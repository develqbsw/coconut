package sk.qbsw.security.oauth.base.configuration;

/**
 * The OAuth validation configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.2
 */
public interface OAuthValidationConfigurator
{
	/**
	 * The master token expire limit in hours. @return the master token expire limit
	 *
	 * @return the master token expire limit
	 */
	Integer getMasterTokenExpireLimit ();

	/**
	 * The authentication token expire limit in hours. @return the authentication token expire limit
	 *
	 * @return the authentication token expire limit
	 */
	Integer getAuthenticationTokenExpireLimit ();

	/**
	 * The change limit in hours - ak je < 1, tak ignorovat, beriem to tak, ze je to vypnute lebo to niekto chcel vypnut. @return the master token change limit
	 *
	 * @return the master token change limit
	 */
	Integer getMasterTokenChangeLimit ();

	/**
	 * The authentication token change limit. @return the authentication token change limit
	 *
	 * @return the authentication token change limit
	 */
	Integer getAuthenticationTokenChangeLimit ();
}
