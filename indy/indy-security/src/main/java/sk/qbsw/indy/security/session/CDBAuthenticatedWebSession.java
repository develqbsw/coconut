package sk.qbsw.indy.security.session;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.authentication.model.AuthenticationSecurityToken;
import sk.qbsw.security.authentication.model.spring.UsernamePasswordUnitAuthenticationToken;
import sk.qbsw.security.core.model.domain.Account;

/**
 * Session of the logged user.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.0.0
 */
public class CDBAuthenticatedWebSession extends AIndySecurityAuthenticatedWebSession
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CLdapAuthenticatedWebSession.class);

	/** The login service. */
	@SpringBean (name = "cLoginService")
	private AuthenticationService loginService;

	/**
	 * Gets actual session.
	 *
	 * @return active session
	 */
	public static CDBAuthenticatedWebSession get ()
	{
		return (CDBAuthenticatedWebSession) AIndySecurityAuthenticatedWebSession.get();
	}

	/**
	 * Instantiates a new authenticated session.
	 *
	 * @param request the request
	 */
	public CDBAuthenticatedWebSession (Request request)
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
	public boolean authenticate (AuthenticationSecurityToken authenticationToken)
	{
		//get supported token
		UsernamePasswordUnitAuthenticationToken token = getAuthenticationObject(authenticationToken);
		if (token == null)
		{
			LOGGER.error("The DB authentication session doesn't support given authentication token type");
			setSecurityException(new CSecurityException("The DB authentication session doesn't support given authentication token type"));
			return false;
		}

		try
		{
			Account user = null;

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
