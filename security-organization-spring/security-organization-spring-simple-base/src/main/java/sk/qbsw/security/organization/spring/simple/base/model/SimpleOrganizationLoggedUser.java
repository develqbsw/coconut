package sk.qbsw.security.organization.spring.simple.base.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.base.model.LoggedUser;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

/**
 * The simple organization logged user.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
@Getter
public class SimpleOrganizationLoggedUser extends LoggedUser implements SimpleOrganizationAccountDetails
{
	private static final long serialVersionUID = -198736486895065544L;

	@NotNull
	private SimpleOrganization organization;

	/**
	 * Instantiates a new Simple organization logged user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param organization the organization
	 */
	public SimpleOrganizationLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, SimpleOrganization organization)
	{
		super(id, username, password, authorities);
		this.organization = organization;
	}

	/**
	 * Instantiates a new Simple organization logged user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param organization the organization
	 * @param additionalInformation the additional information
	 */
	public SimpleOrganizationLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, SimpleOrganization organization, Map<String, Object> additionalInformation)
	{
		super(id, username, password, authorities, additionalInformation);
		this.organization = organization;
	}

	@Override
	public boolean equals (Object rhs)
	{
		return super.equals(rhs);
	}

	@Override
	public int hashCode ()
	{
		return super.hashCode();
	}
}
