package sk.qbsw.security.core.configuration.model;

/**
 * The helper parameters enum
 *
 * @author Marek Martinkovic
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.14.3
 */
public enum AdditionalAuthenticationParamsTypes
{
	/**
	 * The custom auth parameter REALM for web digest auth
	 */
	REALM,

	/**
	 * perform hash into lowercase
	 */
	HASH_TO_LOWERCASE
}
