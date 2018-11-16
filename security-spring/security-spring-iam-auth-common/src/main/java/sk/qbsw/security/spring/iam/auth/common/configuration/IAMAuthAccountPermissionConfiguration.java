package sk.qbsw.security.spring.iam.auth.common.configuration;

import java.io.Serializable;

/**
 * The IAM auth account permission configuration.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
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
