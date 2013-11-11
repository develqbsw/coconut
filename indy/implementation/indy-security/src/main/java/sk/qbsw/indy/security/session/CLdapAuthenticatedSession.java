package sk.qbsw.indy.security.session;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

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

	/** The login service. */
	@SpringBean (name = "ldapAuthenticationService")
	private IAuthenticationService loginService;
	
	/** The organization unit. */
	private String organizationUnit;
	
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
			setOrganizationUnit(user.getOrganizationUnit());
			
			return true;
		}
		catch (CSecurityException e)
		{
			setSecurityException(e);
			return false;
		}
	}

	/**
	 * Gets the organization unit.
	 *
	 * @return the organization unit
	 */
	public String getOrganizationUnit ()
	{
		return organizationUnit;
	}

	/**
	 * Sets the organization unit.
	 *
	 * @param organizationUnit the new organization unit
	 */
	public void setOrganizationUnit (String organizationUnit)
	{
		this.organizationUnit = organizationUnit;
	}
}
