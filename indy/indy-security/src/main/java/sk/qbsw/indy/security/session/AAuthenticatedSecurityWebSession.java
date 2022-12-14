package sk.qbsw.indy.security.session;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Organization;

/**
 * Abstract session class to create new session objects base on wicket web sessio.
 * @see org.apache.wicket.authroles.authentication.AuthenticatedWebSession
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
public abstract class AAuthenticatedSecurityWebSession extends AAuthenticatedWebSession
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The organization. */
	private Organization organization;

	/** The logged user. */
	private Account user;

	/** The organization unit. */
	private String unit;

	/** The security exception. */
	protected CSecurityException securityException;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AAuthenticatedSecurityWebSession.class);

	/**
	 * Instantiates a new a authenticated session.
	 *
	 * @param request the request
	 */
	public AAuthenticatedSecurityWebSession (Request request)
	{
		super(request);
	}

	/**
	 * Gets the.
	 *
	 * @return Current authenticated session
	 */
	public static AAuthenticatedSecurityWebSession get ()
	{
		return (AAuthenticatedSecurityWebSession) AAuthenticatedWebSession.get();
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession#getRoles()
	 */
	@Override
	public Roles getRoles ()
	{
		Roles roles = new Roles();

		if (isSignedIn())
		{
			roles.addAll(user.exportRoles());
		}

		if (roles.isEmpty())
		{
			LOGGER.warn("No roles found.");
		}

		return roles;
	}

	/**
	 * Returns true if logged user has assigned input role.
	 *
	 * @param role input role
	 * @return boolean value
	 */
	public final boolean hasRole (String role)
	{
		Boolean hasRole = Boolean.FALSE;
		if (getRoles() != null)
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
	public Organization getOrganization ()
	{
		return organization;
	}

	/**
	 * Sets the organization.
	 *
	 * @param organization the new organization
	 */
	public void setOrganization (Organization organization)
	{
		this.organization = organization;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Account getUser ()
	{
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	protected void setUser (Account user)
	{
		this.user = user;
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public String getUnit ()
	{
		return unit;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit the new unit
	 */
	protected void setUnit (String unit)
	{
		this.unit = unit;
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
	protected void setSecurityException (CSecurityException securityException)
	{
		this.securityException = securityException;
	}
}
