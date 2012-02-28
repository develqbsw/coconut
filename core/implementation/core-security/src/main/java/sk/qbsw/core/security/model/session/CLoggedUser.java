/**
 * 
 */
package sk.qbsw.core.security.model.session;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import sk.qbsw.core.security.model.domain.CLicense;
import sk.qbsw.core.security.model.domain.COrganization;


/**
 * The Class CLoggedUser.
 *
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings ("serial")
public class CLoggedUser extends User
{

	/** The organization. */
	private COrganization organization;

	/** The actual valid license. */
	private CLicense<?> actualValidLicense;

	/**
	 * Instantiates a new c logged user.
	 *
	 * @param organization the organization
	 * @param actualValidLicense the actual valid license
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 * @param accountNonExpired the account non expired
	 * @param credentialsNonExpired the credentials non expired
	 * @param accountNonLocked the account non locked
	 * @param authorities the authorities
	 */
	public CLoggedUser (COrganization organization, CLicense<?> actualValidLicense, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
	{
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.organization = organization;
		this.actualValidLicense = actualValidLicense;
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
	 * Gets the actual valid license.
	 *
	 * @return the actual valid license
	 */
	public CLicense<?> getActualValidLicense ()
	{
		return actualValidLicense;
	}

	/**
	 * Sets the actual valid license.
	 *
	 * @param actualValidLicense the new actual valid license
	 */
	public void setActualValidLicense (CLicense<?> actualValidLicense)
	{
		this.actualValidLicense = actualValidLicense;
	}

}
