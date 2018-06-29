package sk.qbsw.security.spring.system.service;

/**
 * The system authentication security context service.
 *
 * @author Tomas Lauro
 * @version 1.18.6
 * @since 1.18.6
 */
public interface SystemAuthenticationSecurityContextService
{
	/**
	 * Create authentication.
	 *
	 * @param organizationId the organization id
	 */
	void createAuthentication (Long organizationId);

	/**
	 * Clear authentication.
	 */
	void clearAuthentication ();
}
