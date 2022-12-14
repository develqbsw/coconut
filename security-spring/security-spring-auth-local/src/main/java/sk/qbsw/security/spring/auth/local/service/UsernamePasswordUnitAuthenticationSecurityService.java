package sk.qbsw.security.spring.auth.local.service;

import org.springframework.security.core.Authentication;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.spring.auth.base.model.UsernamePasswordUnitAuthenticationToken;
import sk.qbsw.security.spring.auth.base.service.AuthenticationSecurityService;
import sk.qbsw.security.spring.common.model.LoggedAccount;
import sk.qbsw.security.spring.common.service.AuthorityConverter;

/**
 * The spring authentication service uses the authentication service to authenticate user.
 *
 * @author Tomas Lauro
 * @author Dalibor Rak
 * @version 2.0.0
 * @since 1.13.4
 */
public class UsernamePasswordUnitAuthenticationSecurityService extends AService implements AuthenticationSecurityService
{
	private final AuthenticationService authenticationService;

	private final AuthorityConverter authorityConverter;

	/**
	 * Instantiates a new Username password unit authentication security service.
	 *
	 * @param authenticationService the authentication service
	 * @param authorityConverter the authority converter
	 */
	public UsernamePasswordUnitAuthenticationSecurityService (AuthenticationService authenticationService, AuthorityConverter authorityConverter)
	{
		this.authenticationService = authenticationService;
		this.authorityConverter = authorityConverter;
	}

	@Override
	public LoggedAccount authenticate (Authentication authentication) throws CSecurityException
	{
		// checks token support
		if (authentication == null || !this.supports(authentication.getClass()))
		{
			throw new CSecurityException(ECoreErrorResponse.UNSUPPORTED_AUTHENTICATION_TOKEN);
		}

		UsernamePasswordUnitAuthenticationToken usernamePasswordAuthentication = (UsernamePasswordUnitAuthenticationToken) authentication;

		if (usernamePasswordAuthentication.getUnit() != null)
		{
			Account account = authenticationService.login((String) usernamePasswordAuthentication.getPrincipal(), (String) usernamePasswordAuthentication.getCredentials(), usernamePasswordAuthentication.getUnit());
			return convertAccountToLoggedAccount(account);
		}
		else
		{
			Account account = authenticationService.login((String) usernamePasswordAuthentication.getPrincipal(), (String) usernamePasswordAuthentication.getCredentials());
			return convertAccountToLoggedAccount(account);
		}
	}

	private LoggedAccount convertAccountToLoggedAccount (Account account)
	{
		// Organization organization = new Organization(account.getOrganization().getId(), account.getOrganization().getCode(), account.getOrganization().getName());
		return new LoggedAccount(account.getId(), account.getLogin(), account.getPassword(), authorityConverter.convertRolesToAuthorities(account.exportRoles()));// , organization);

	}

	@Override
	public boolean supports (Class<? extends Authentication> authentication)
	{
		return UsernamePasswordUnitAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
