package sk.qbsw.security.spring.iam.auth.base.configuration;

import java.io.Serializable;

/**
 * The IAM auth account permission configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface IAMAuthAccountPermissionConfiguration extends Serializable
{
	/**
	 * Gets account default group code.
	 *
	 * @return the account default group code
	 */
	String getAccountDefaultGroupCode ();
}
