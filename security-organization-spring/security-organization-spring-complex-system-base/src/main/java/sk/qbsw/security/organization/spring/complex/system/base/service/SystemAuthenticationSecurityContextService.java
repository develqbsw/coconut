package sk.qbsw.security.organization.spring.complex.system.base.service;

/**
 * The system authentication security context service.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
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
