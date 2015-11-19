package sk.qbsw.indy.security.session;

import org.apache.wicket.request.Request;

import sk.qbsw.core.security.model.CUsernamePasswordAuthenticationToken;
import sk.qbsw.core.security.model.CUsernamePasswordUnitAuthenticationToken;
import sk.qbsw.core.security.model.IAuthenticationToken;
import sk.qbsw.core.security.model.spring.CUsernamePasswordUnitAuthentication;

/**
 * Authentication for Wicket using Spring security.
 *
 * @author Tomas Lauro
 * @version 1.13.4
 * @since 1.13.4
 */
public abstract class AIndySecurityAuthenticatedWebSession extends AAuthenticatedSecurityWebSession
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new a indy security authenticated web session.
	 *
	 * @param request the request
	 */
	public AIndySecurityAuthenticatedWebSession (Request request)
	{
		super(request);
	}

	/**
	 * Gets the.
	 *
	 * @return the a indy security authenticated web session
	 */
	public static AIndySecurityAuthenticatedWebSession get ()
	{
		return (AIndySecurityAuthenticatedWebSession) AAuthenticatedWebSession.get();
	}

	/**
	 * Gets the authentication object from authentication token. Of given token is not supported return null.
	 *
	 * @param authenticationToken the authentication token
	 * @return the authentication object or null
	 */
	protected CUsernamePasswordUnitAuthentication getAuthenticationObject (IAuthenticationToken authenticationToken)
	{
		//first of all compare the CUsernamePasswordUnitAuthenticationToken because it has parent CUsernamePasswordAuthenticationToken
		if (CUsernamePasswordUnitAuthenticationToken.class.isAssignableFrom(authenticationToken.getClass()) == true)
		{
			return new CUsernamePasswordUnitAuthentication((String) authenticationToken.getPrincipal(), (String) authenticationToken.getCredentials(), ((sk.qbsw.core.security.model.CUsernamePasswordUnitAuthenticationToken) authenticationToken).getUnit());
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
}
