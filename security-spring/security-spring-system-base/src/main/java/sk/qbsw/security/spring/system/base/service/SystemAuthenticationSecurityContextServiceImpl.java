package sk.qbsw.security.spring.system.base.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import sk.qbsw.security.spring.system.common.model.SystemAuthenticationToken;

/**
 * The system authentication context service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.6
 */
public class SystemAuthenticationSecurityContextServiceImpl implements SystemAuthenticationSecurityContextService
{
	private final AuthenticationProvider systemAuthenticationProvider;

	/**
	 * Instantiates a new System authentication security context service.
	 *
	 * @param systemAuthenticationProvider the system authentication provider
	 */
	public SystemAuthenticationSecurityContextServiceImpl (AuthenticationProvider systemAuthenticationProvider)
	{
		this.systemAuthenticationProvider = systemAuthenticationProvider;
	}

	public void createAuthentication ()
	{
		Authentication populatedAuthentication = systemAuthenticationProvider.authenticate(new SystemAuthenticationToken());
		SecurityContextHolder.getContext().setAuthentication(populatedAuthentication);
	}

	public void clearAuthentication ()
	{
		SecurityContextHolder.getContext().setAuthentication(null);
	}
}
