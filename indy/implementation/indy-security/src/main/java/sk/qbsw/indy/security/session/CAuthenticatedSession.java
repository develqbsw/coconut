package sk.qbsw.indy.security.session;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.service.IAuthenticationService;


/**
 * Session of the logged user
 * 
 * @author Dalibor Rak
 * @version 1.0.0
 * @since 1.0.0
 */
public class CAuthenticatedSession extends AuthenticatedWebSession
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private IAuthenticationService loginService;

	private COrganization organization;
	
	private CUser user;

	/**
	 * Gets actual session
	 * @return active session
	 */
	public static CAuthenticatedSession get ()
	{
		return (CAuthenticatedSession) AuthenticatedWebSession.get();
	}

	public CAuthenticatedSession (Request request)
	{
		super(request);
		Injector.get().inject(this);
	}

	/**
	 * Authenticates the user for web checkin
	 * 
	 * @oaram login is represented by surname
	 * @param password is represented by ticketNumber
	 */
	public boolean authenticate (String login, String password)
	{
		
		return loginService.canLogin(login, password);
	}

	@Override
	public Roles getRoles ()
	{
		if (isSignedIn())
		{
			Roles roles = new Roles();
			
			for (CGroup group : user.getGroups())
			{
				for (CRole role : group.getRoles())
				{
					roles.add(role.getCode());
				}
				
			}
			
			return roles;
		}
		return null;
	}

	public COrganization getOrganization ()
	{
		return organization;
	}

	public void setOrganization (COrganization organization)
	{
		this.organization = organization;
	}

	public CUser getUser ()
	{
		return user;
	}

	public void setUser (CUser user)
	{
		this.user = user;
	}
	
	

}
