package sk.qbsw.indy.security.session;

import org.apache.wicket.request.Request;
import org.springframework.security.core.Authentication;

import sk.qbsw.security.authentication.model.CustomUsernamePasswordAuthenticationToken;
import sk.qbsw.security.authentication.model.CustomUsernamePasswordUnitAuthenticationToken;
import sk.qbsw.security.authentication.model.CustomAuthenticationToken;
import sk.qbsw.security.authentication.model.spring.UsernamePasswordUnitAuthentication;
import sk.qbsw.security.core.model.domain.CUser;

/**
 * Authentication for Wicket using Spring security.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.6.0
 */
public class CSpringSecurityAuthenticatedSession extends ASpringSecurityAuthenticatedSession
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8116389832639807281L;

	/**
	 * Instantiates a new c spring ws authenticated session.
	 *
	 * @param request the request
	 */
	public CSpringSecurityAuthenticatedSession (Request request)
	{
		super(request);
	}

	/**
	 * Gets the.
	 *
	 * @return the c spring security authenticated session
	 */
	public static CSpringSecurityAuthenticatedSession get ()
	{
		return (CSpringSecurityAuthenticatedSession) AAuthenticatedWebSession.get();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.indy.security.session.ASpringSecurityAuthenticatedSession#getAuthenticationObject(sk.qbsw.core.security.model.IAuthenticationToken)
	 */
	@Override
	protected Authentication transformAuthenticationObject (CustomAuthenticationToken authenticationToken)
	{
		//first of all compare the CustomUsernamePasswordUnitAuthenticationToken because it has parent CustomUsernamePasswordAuthenticationToken
		if (CustomUsernamePasswordUnitAuthenticationToken.class.isAssignableFrom(authenticationToken.getClass()) == true)
		{
			return new UsernamePasswordUnitAuthentication((String) authenticationToken.getPrincipal(), (String) authenticationToken.getCredentials(), ((CustomUsernamePasswordUnitAuthenticationToken) authenticationToken).getUnit());
		}
		else if (CustomUsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationToken.getClass()) == true)
		{
			return new UsernamePasswordUnitAuthentication((String) authenticationToken.getPrincipal(), (String) authenticationToken.getCredentials(), null);
		}
		else
		{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.indy.security.session.ASpringSecurityAuthenticatedSession#prepareSessionData(org.springframework.security.core.Authentication)
	 */
	@Override
	protected boolean prepareSessionData (Authentication authentication)
	{
		if (UsernamePasswordUnitAuthentication.class.isAssignableFrom(authentication.getClass()) == true)
		{
			UsernamePasswordUnitAuthentication token = (UsernamePasswordUnitAuthentication) authentication;

			CUser user = (CUser) token.getDetails();
			String unit = token.getUnit();

			super.setUser(user);
			super.setOrganization(user.getOrganization());
			super.setUnit(unit);

			return true;
		}
		else
		{
			return false;
		}
	}
}
