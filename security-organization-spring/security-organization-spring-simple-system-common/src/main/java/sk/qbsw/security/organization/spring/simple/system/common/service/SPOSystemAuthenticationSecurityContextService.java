package sk.qbsw.security.organization.spring.simple.system.common.service;

/**
 * The system authentication security context service.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 2.2.0
 * @since 1.18.6
 */
public interface SPOSystemAuthenticationSecurityContextService
{
	/**
	 * Create authentication.
	 *
	 * @param organizationCode the organization code
	 */
	void createAuthentication (String organizationCode);

	/**
	 * Clear authentication.
	 */
	void clearAuthentication ();
}
