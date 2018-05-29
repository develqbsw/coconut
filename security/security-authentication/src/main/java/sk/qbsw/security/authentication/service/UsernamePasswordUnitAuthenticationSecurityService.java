package sk.qbsw.security.authentication.service;

import org.springframework.security.core.Authentication;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.authentication.spring.auth.model.UsernamePasswordUnitAuthenticationToken;
import sk.qbsw.security.authentication.spring.auth.service.AuthenticationSecurityService;
import sk.qbsw.security.authentication.spring.model.LoggedUser;
import sk.qbsw.security.authentication.spring.model.Organization;
import sk.qbsw.security.authentication.spring.service.AuthorityConverter;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The spring authentication service uses the authentication service to authenticate user.
 *
 * @author Tomas Lauro
 * @author Dalibor Rak
 * @version 1.19.0
 * @since 1.13.4
 */
public class UsernamePasswordUnitAuthenticationSecurityService extends AService implements AuthenticationSecurityService
{
	private final AuthenticationService authenticationService;

	private final AuthorityConverter authorityConverter;

	public UsernamePasswordUnitAuthenticationSecurityService (AuthenticationService authenticationService, AuthorityConverter authorityConverter)
	{
		this.authenticationService = authenticationService;
		this.authorityConverter = authorityConverter;
	}

	@Override
	public LoggedUser authenticate (Authentication authentication) throws CSecurityException
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
			return convertAccountToLoggedUser(account);
		}
		else
		{
			Account account = authenticationService.login((String) usernamePasswordAuthentication.getPrincipal(), (String) usernamePasswordAuthentication.getCredentials());
			return convertAccountToLoggedUser(account);
		}
	}

	private LoggedUser convertAccountToLoggedUser (Account account)
	{
		Organization organization = Organization.newBuilder() //
			.id(account.getOrganization().getId()) //
			.code(account.getOrganization().getCode()) //
			.name(account.getOrganization().getName()) //
			.build();

		return new LoggedUser(account.getId(), account.getLogin(), account.getPassword(), authorityConverter.convertRolesToAuthorities(account.exportRoles()), organization);

	}

	@Override
	public boolean supports (Class<? extends Authentication> authentication)
	{
		return UsernamePasswordUnitAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
