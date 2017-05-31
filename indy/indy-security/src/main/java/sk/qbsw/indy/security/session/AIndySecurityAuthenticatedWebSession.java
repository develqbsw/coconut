package sk.qbsw.indy.security.session;

import org.apache.wicket.request.Request;

import sk.qbsw.security.authentication.model.CustomUsernamePasswordAuthenticationToken;
import sk.qbsw.security.authentication.model.CustomUsernamePasswordUnitAuthenticationToken;
import sk.qbsw.security.authentication.model.CustomAuthenticationToken;
import sk.qbsw.security.authentication.model.spring.UsernamePasswordUnitAuthentication;

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
	protected UsernamePasswordUnitAuthentication getAuthenticationObject (CustomAuthenticationToken authenticationToken)
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
}
