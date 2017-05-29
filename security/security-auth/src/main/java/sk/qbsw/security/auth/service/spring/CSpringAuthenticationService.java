package sk.qbsw.security.auth.service.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.auth.base.service.IAuthenticationService;
import sk.qbsw.security.auth.model.spring.CUsernamePasswordUnitAuthentication;
import sk.qbsw.security.core.model.domain.CUser;

/**
 * The spring authentication service uses the authentication service to authenticate user.
 *
 * @author Tomas Lauro
 * @author Dalibor Rak
 * 
 * @version 1.13.4
 * @since 1.13.4
 */
@Service (value = "springAuthenticationService")
public class CSpringAuthenticationService extends AService implements ISpringAuthenticationService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 800407865927978145L;
	
	/** The authentication service. */
	@Autowired
	private IAuthenticationService authenticationService;
	
	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.ISpringAuthenticationService#login(org.springframework.security.core.Authentication)
	 */
	@Override
	public CUser login (Authentication authentication) throws CSecurityException
	{
		//checks token support
		if (authentication == null || this.supports(authentication.getClass()) == false)
		{
			throw new CSecurityException(ECoreErrorResponse.UNSUPPORTED_AUTHENTICATION_TOKEN);
		}

		CUsernamePasswordUnitAuthentication usernamePasswordAuthentication = (CUsernamePasswordUnitAuthentication) authentication;

		if (usernamePasswordAuthentication.getUnit() != null)
		{
			return authenticationService.login((String) usernamePasswordAuthentication.getPrincipal(), (String) usernamePasswordAuthentication.getCredentials(), usernamePasswordAuthentication.getUnit());
		}
		else
		{
			return authenticationService.login((String) usernamePasswordAuthentication.getPrincipal(), (String) usernamePasswordAuthentication.getCredentials());
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.ISpringAuthenticationService#supports(java.lang.Class)
	 */
	@Override
	public boolean supports (Class<? extends Authentication> authentication)
	{
		return CUsernamePasswordUnitAuthentication.class.isAssignableFrom(authentication);
	}
}
