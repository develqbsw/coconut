package sk.qbsw.indy.security.session;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.service.IAuthenticationService;

/**
 * Session of the logged user.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.0.0
 */
public class CDBAuthenticatedWebSession extends AAuthenticatedSecurityWebSession
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CLdapAuthenticatedWebSession.class);

	/** The login service. */
	@SpringBean (name = "cLoginService")
	private IAuthenticationService loginService;

	/**
	 * Gets actual session.
	 *
	 * @return active session
	 */
	public static CDBAuthenticatedWebSession get ()
	{
		return (CDBAuthenticatedWebSession) AAuthenticatedSecurityWebSession.get();
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

	/**
	 * Authenticates the user for web.
	 *
	 * @param login the login
	 * @param password is represented by ticketNumber
	 * @return true, if successful
	 */
	public boolean authenticate (String login, String password)
	{
		try
		{
			CUser user = loginService.login(login, password);
			setOrganization(user.getOrganization());
			setUser(user);

			return true;
		}
		catch (CSecurityException e)
		{
			LOGGER.warn(String.format("User '%s' failed to login. Reason: %s %s", login, e.getErrorCode(), e.getMessage()));
			setSecurityException(e);
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.indy.security.session.AAuthenticatedSession#authenticate(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authenticate (String login, String password, String unit)
	{
		try
		{
			CUser user = loginService.login(login, password, unit);
			setOrganization(user.getOrganization());
			setUser(user);

			return true;
		}
		catch (CSecurityException e)
		{
			LOGGER.warn(String.format("User '%s' failed to login. Reason: %s %s", login, e.getErrorCode(), e.getMessage()));
			setSecurityException(e);
			return false;
		}
	}
}
