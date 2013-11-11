package sk.qbsw.indy.security.session;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Abstract session class to create new session objects base on wicket web sessio.
 * @see org.apache.wicket.authroles.authentication.AuthenticatedWebSession
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.7.0
 * @since 1.7.0
 */
public abstract class AAuthenticatedSession extends AuthenticatedWebSession
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The organization. */
	protected COrganization organization;

	/** The user. */
	protected CUser user;
	
	/** The security exception. */
	protected CSecurityException securityException;
	
	/**
	 * Instantiates a new a authenticated session.
	 *
	 * @param request the request
	 */
	public AAuthenticatedSession (Request request)
	{
		super(request);
	}
	
	/**
	 * @return Current authenticated session
	 */
	public static AAuthenticatedSession get ()
	{
		return (AAuthenticatedSession) AuthenticatedWebSession.get();
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession#getRoles()
	 */
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
	
	/**
	 * Returns true if logged user has assigned input role.
	 *
	 * @param role input role
	 * @return boolean value
	 */
	public boolean hasRole (String role)
	{
		Boolean hasRole = Boolean.FALSE;
		if (getRoles() == null)
		{
			hasRole = Boolean.FALSE;
		}
		else
		{
			hasRole = getRoles().hasAnyRole(new Roles(role));
		}
		return hasRole;
	} 

	/**
	 * Gets the organization.
	 *
	 * @return the organization
	 */
	public COrganization getOrganization ()
	{
		return organization;
	}

	/**
	 * Sets the organization.
	 *
	 * @param organization the new organization
	 */
	public void setOrganization (COrganization organization)
	{
		this.organization = organization;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public CUser getUser ()
	{
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser (CUser user)
	{
		this.user = user;
	}

	/**
	 * Gets the security exception.
	 *
	 * @return the security exception
	 */
	public CSecurityException getSecurityException ()
	{
		return securityException;
	}

	/**
	 * Sets the security exception.
	 *
	 * @param securityException the new security exception
	 */
	public void setSecurityException (CSecurityException securityException)
	{
		this.securityException = securityException;
	}
}
