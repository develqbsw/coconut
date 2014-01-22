package sk.qbsw.indy.security.session;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.service.IAuthenticationService;

/**
 * Session of the logged LDAP user.
 *
 * @author Tomas Lauro
 * @version 1.7.0
 * @since 1.7.0
 */
public class CLdapAuthenticatedSession extends AAuthenticatedSession
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CLdapAuthenticatedSession.class);

	/** The login service. */
	@SpringBean (name = "ldapAuthenticationService")
	private IAuthenticationService loginService;

	/** The organization unit. */
	//private String organizationUnit;

	/**
	 * Gets actual session.
	 *
	 * @return active session
	 */
	public static CLdapAuthenticatedSession get ()
	{
		return (CLdapAuthenticatedSession) AAuthenticatedSession.get();
	}

	/**
	 * Instantiates a new ldap authenticated session.
	 *
	 * @param request the request
	 */
	public CLdapAuthenticatedSession (Request request)
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
	 * Authenticates the user for web checkin.
	 *
	 * @param login the login
	 * @param password is represented by ticketNumber
	 * @return true, if successful
	 */
	public boolean authenticate (String login, String password)
	{
		try
		{
			user = loginService.login(login, password);

			setOrganization(user.getOrganization());
			//setOrganizationUnit(user.getOrganizationUnit());

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
