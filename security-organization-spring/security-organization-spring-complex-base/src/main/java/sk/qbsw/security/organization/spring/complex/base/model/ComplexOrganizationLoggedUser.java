package sk.qbsw.security.organization.spring.complex.base.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.base.model.LoggedUser;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The complex organization logged user.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
public class ComplexOrganizationLoggedUser extends LoggedUser implements ComplexOrganizationAccountDetails
{
	private static final long serialVersionUID = -198736486895065544L;

	@NotNull
	private final Long userId;

	@NotNull
	private final List<ComplexOrganization> organizations;

	/**
	 * Instantiates a new Simple organization logged user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param userId the user id
	 * @param organizations the organizations
	 */
	public ComplexOrganizationLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, List<ComplexOrganization> organizations)
	{
		super(id, username, password, authorities);
		this.userId = userId;
		this.organizations = organizations;
	}

	/**
	 * Instantiates a new Simple organization logged user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param userId the user id
	 * @param organizations the organizations
	 * @param additionalInformation the additional information
	 */
	public ComplexOrganizationLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, List<ComplexOrganization> organizations, Map<String, Object> additionalInformation)
	{
		super(id, username, password, authorities, additionalInformation);
		this.userId = userId;
		this.organizations = organizations;
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
