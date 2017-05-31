package sk.qbsw.indy.security.session;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.authentication.model.CustomAuthenticationToken;
import sk.qbsw.security.authentication.model.spring.UsernamePasswordUnitAuthentication;
import sk.qbsw.security.core.model.domain.CUser;

/**
 * Session of the logged LDAP user.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.7.0
 */
public class CLdapAuthenticatedWebSession extends AIndySecurityAuthenticatedWebSession
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CLdapAuthenticatedWebSession.class);

	/** The login service. */
	@SpringBean (name = "ldapAuthenticationService")
	private AuthenticationService loginService;

	/**
	 * Gets actual session.
	 *
	 * @return active session
	 */
	public static CLdapAuthenticatedWebSession get ()
	{
		return (CLdapAuthenticatedWebSession) AIndySecurityAuthenticatedWebSession.get();
	}

	/**
	 * Instantiates a new ldap authenticated session.
	 *
	 * @param request the request
	 */
	public CLdapAuthenticatedWebSession (Request request)
	{
		super(request);
		injectDependencies();
	}

	/**
	 * Inject dependencies.
	 */
	private void injectDependencies ()
	{
		Injector.get().inject(this);

		if (loginService == null)
		{
			throw new IllegalStateException("A loginService is required.");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.indy.security.session.AAuthenticatedWebSession#authenticate(sk.qbsw.core.security.model.IAuthenticationToken)
	 */
	@Override
	public boolean authenticate (CustomAuthenticationToken authenticationToken)
	{
		//get supported token
		UsernamePasswordUnitAuthentication token = getAuthenticationObject(authenticationToken);

		//checks if there is a supported token
		if (token == null)
		{
			LOGGER.error("The LDAP authentication session doesn't support given authentication token type");
			setSecurityException(new CSecurityException("The LDAP authentication session doesn't support given authentication token type"));
			return false;
		}

		try
		{
			CUser user = null;

			if (token.getUnit() != null)
			{
				user = loginService.login((String) token.getPrincipal(), (String) token.getCredentials(), token.getUnit());
			}
			else
			{
				user = loginService.login((String) token.getPrincipal(), (String) token.getCredentials());
			}
			setOrganization(user.getOrganization());
			setUser(user);

			return true;
		}
		catch (CSecurityException e)
		{
			LOGGER.warn(String.format("User '%s' failed to login. Reason: %s", (String) token.getPrincipal(), e.toString()));
			setSecurityException(e);
			return false;
		}
	}
}
