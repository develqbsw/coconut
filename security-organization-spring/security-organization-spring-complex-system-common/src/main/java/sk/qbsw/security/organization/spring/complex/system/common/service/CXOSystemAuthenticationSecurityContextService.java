package sk.qbsw.security.organization.spring.complex.system.common.service;

/**
 * The system authentication security context service.
 *
 * @author Tomas Leken
 * @version 2.1.0
 * @since 2.0.0
 */
public interface CXOSystemAuthenticationSecurityContextService
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
