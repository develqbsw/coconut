package sk.qbsw.security.organization.spring.simple.system.common.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import sk.qbsw.security.spring.system.common.model.SystemAuthenticationToken;

/**
 * The system authentication context service.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 2.2.0
 * @since 1.18.6
 */
public class SPOSystemAuthenticationSecurityContextServiceImpl implements SPOSystemAuthenticationSecurityContextService
{
	private final AuthenticationProvider systemAuthenticationProvider;

	/**
	 * Instantiates a new System authentication security context service.
	 *
	 * @param systemAuthenticationProvider the system authentication provider
	 */
	public SPOSystemAuthenticationSecurityContextServiceImpl (AuthenticationProvider systemAuthenticationProvider)
	{
		this.systemAuthenticationProvider = systemAuthenticationProvider;
	}

	public void createAuthentication (String organizationCode)
	{
		SystemAuthenticationToken authenticationToken = new SystemAuthenticationToken();
		authenticationToken.setDetails(organizationCode);

		Authentication populatedAuthentication = systemAuthenticationProvider.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(populatedAuthentication);
	}

	public void clearAuthentication ()
	{
		SecurityContextHolder.getContext().setAuthentication(null);
	}
}
