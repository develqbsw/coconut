package sk.qbsw.security.spring.system.common.service;

/**
 * The system authentication security context service.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.18.6
 */
public interface SystemAuthenticationSecurityContextService
{
	/**
	 * Create authentication.
	 */
	void createAuthentication ();

	/**
	 * Clear authentication.
	 */
	void clearAuthentication ();
}
