package sk.qbsw.core.security.service.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.service.CService;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.spring.CUsernamePasswordUnitAuthenticationToken;
import sk.qbsw.core.security.service.IAuthenticationService;

/**
 * Provider for spring security.
 * 
 * @author Dalibor Rak
 * @author Tomas lauro
 * 
 * @version 1.11.5
 * @since 1.6.0
 * 
 */
@Component ("customAuthenticationProvider")
public class CSecurityAuthenticationProvider extends CService implements AuthenticationProvider
{

	/** The authentication service. */
	@Autowired
	private IAuthenticationService authenticationService;

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate (Authentication authentication) throws AuthenticationException
	{
		Authentication retVal = null;
		try
		{
			if (authentication instanceof CUsernamePasswordUnitAuthenticationToken)
			{
				retVal = authenticate((CUsernamePasswordUnitAuthenticationToken) authentication);
			}
		}
		catch (CSecurityException e)
		{
			throw new AuthenticationServiceException("User not authenticated", e);
		}

		return retVal;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports (Class<? extends Object> authentication)
	{
		return authentication.equals(CUsernamePasswordUnitAuthenticationToken.class);
	}

	/**
	 * Authenticate user by login, password and unit.
	 *
	 * @param token the token
	 * @return the authentication
	 * @throws CSecurityException the c security exception
	 */
	private Authentication authenticate (CUsernamePasswordUnitAuthenticationToken token) throws CSecurityException
	{
		String login = token.getName();
		String password = (String) token.getCredentials();
		String unit = token.getUnit();

		CUser user = null;
		if (unit != null)
		{
			user = authenticationService.login(login, password, unit);
		}
		else
		{
			user = authenticationService.login(login, password);
		}

		List<String> roles = user.exportRoles();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles)
		{
			authorities.add(new GrantedAuthorityImpl(role));
		}

		CUsernamePasswordUnitAuthenticationToken retVal = new CUsernamePasswordUnitAuthenticationToken(login, password, authorities, unit);
		retVal.setDetails(user);

		return retVal;
	}

}
