package sk.qbsw.indy.security.session;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.service.IAuthenticationService;

/**
 * Session of the logged user.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.7.0
 * @since 1.0.0
 */
public class CAuthenticatedSession extends AAuthenticatedSession
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The login service. */
	@SpringBean (name = "cLoginService")
	private IAuthenticationService loginService;

	/**
	 * Gets actual session.
	 *
	 * @return active session
	 */
	public static CAuthenticatedSession get ()
	{
		return (CAuthenticatedSession) AAuthenticatedSession.get();
	}

	/**
	 * Instantiates a new authenticated session.
	 *
	 * @param request the request
	 */
	public CAuthenticatedSession (Request request)
	{
		super(request);
		Injector.get().inject(this);
	}

	/**
	 * Authenticates the user for web checkin.
	 *
	 * @param login the login
	 * @param password is represented by ticketNumber
	 * @return true, if successful
	 * @oaram login is represented by surname
	 */
	public boolean authenticate (String login, String password)
	{
		try
		{
			user = loginService.login(login, password);

			setOrganization(user.getOrganization());

			return true;
		}
		catch (CSecurityException e)
		{
			setSecurityException(e);
			return false;
		}
	}
}
