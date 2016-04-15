package sk.qbsw.core.security.model.domain;

/**
 * The hash method to hash data.
 * 
 * @author Marek Martinkovic
 * 
 * @version 1.14.3
 * @since 1.14.3
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
