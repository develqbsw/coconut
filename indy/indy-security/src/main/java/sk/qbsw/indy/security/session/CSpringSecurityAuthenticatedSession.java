package sk.qbsw.indy.security.session;

import org.apache.wicket.request.Request;
import org.springframework.security.core.Authentication;

import sk.qbsw.security.authentication.model.CUsernamePasswordAuthenticationToken;
import sk.qbsw.security.authentication.model.CUsernamePasswordUnitAuthenticationToken;
import sk.qbsw.security.authentication.model.IAuthenticationToken;
import sk.qbsw.security.authentication.model.spring.CUsernamePasswordUnitAuthentication;
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
	protected Authentication transformAuthenticationObject (IAuthenticationToken authenticationToken)
	{
		//first of all compare the CUsernamePasswordUnitAuthenticationToken because it has parent CUsernamePasswordAuthenticationToken
		if (CUsernamePasswordUnitAuthenticationToken.class.isAssignableFrom(authenticationToken.getClass()) == true)
		{
			return new CUsernamePasswordUnitAuthentication((String) authenticationToken.getPrincipal(), (String) authenticationToken.getCredentials(), ((CUsernamePasswordUnitAuthenticationToken) authenticationToken).getUnit());
		}
		else if (CUsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationToken.getClass()) == true)
		{
			return new CUsernamePasswordUnitAuthentication((String) authenticationToken.getPrincipal(), (String) authenticationToken.getCredentials(), null);
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
		if (CUsernamePasswordUnitAuthentication.class.isAssignableFrom(authentication.getClass()) == true)
		{
			CUsernamePasswordUnitAuthentication token = (CUsernamePasswordUnitAuthentication) authentication;

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
