package sk.qbsw.indy.security.session;

import org.apache.wicket.request.Request;

import sk.qbsw.security.authentication.model.UsernamePasswordAuthenticationSecurityToken;
import sk.qbsw.security.authentication.model.UsernamePasswordUnitAuthenticationSecurityToken;
import sk.qbsw.security.authentication.model.AuthenticationSecurityToken;
import sk.qbsw.security.authentication.model.spring.UsernamePasswordUnitAuthenticationToken;

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
	protected UsernamePasswordUnitAuthenticationToken getAuthenticationObject (AuthenticationSecurityToken authenticationToken)
	{
		//first of all compare the UsernamePasswordUnitAuthenticationSecurityToken because it has parent UsernamePasswordAuthenticationSecurityToken
		if (UsernamePasswordUnitAuthenticationSecurityToken.class.isAssignableFrom(authenticationToken.getClass()) == true)
		{
			return new UsernamePasswordUnitAuthenticationToken((String) authenticationToken.getPrincipal(), (String) authenticationToken.getCredentials(), ((UsernamePasswordUnitAuthenticationSecurityToken) authenticationToken).getUnit());
		}
		else if (UsernamePasswordAuthenticationSecurityToken.class.isAssignableFrom(authenticationToken.getClass()) == true)
		{
			return new UsernamePasswordUnitAuthenticationToken((String) authenticationToken.getPrincipal(), (String) authenticationToken.getCredentials(), null);
		}
		else
		{
			return null;
		}
	}
}
