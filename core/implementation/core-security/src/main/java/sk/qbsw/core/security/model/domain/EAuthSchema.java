package sk.qbsw.core.security.model.domain;

/**
 * The hash method to hash data.
 * 
 * @author Tomas Lauro
 * @version 1.8.0
 * @since 1.8.0
 */
public enum EAuthSchema
{
	/**  The custom authenification in legacy projects */
	CUSTOM,
	/**
	 * digest authenticatin with schema  login:realm:passwd
	 */
	DIGEST;


}
