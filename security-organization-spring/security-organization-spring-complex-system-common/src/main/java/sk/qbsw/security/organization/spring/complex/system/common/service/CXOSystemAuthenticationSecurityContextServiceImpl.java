package sk.qbsw.security.organization.spring.complex.system.common.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import sk.qbsw.security.spring.system.common.model.SystemAuthenticationToken;

/**
 * The system authentication context service.
 *
 * @author Tomas Leken
 * @version 2.1.0
 * @since 2.0.0
 */
public class CXOSystemAuthenticationSecurityContextServiceImpl implements CXOSystemAuthenticationSecurityContextService
{
	private final AuthenticationProvider systemAuthenticationProvider;

	/**
	 * Instantiates a new System authentication security context service.
	 *
	 * @param systemAuthenticationProvider the system authentication provider
	 */
	public CXOSystemAuthenticationSecurityContextServiceImpl (AuthenticationProvider systemAuthenticationProvider)
	{
		this.systemAuthenticationProvider = systemAuthenticationProvider;
	}

	public void createAuthentication (Long organizationId)
	{
		SystemAuthenticationToken authenticationToken = new SystemAuthenticationToken();
		authenticationToken.setDetails(organizationId);

		Authentication populatedAuthentication = systemAuthenticationProvider.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(populatedAuthentication);
	}

	public void clearAuthentication ()
	{
		SecurityContextHolder.getContext().setAuthentication(null);
	}
}
