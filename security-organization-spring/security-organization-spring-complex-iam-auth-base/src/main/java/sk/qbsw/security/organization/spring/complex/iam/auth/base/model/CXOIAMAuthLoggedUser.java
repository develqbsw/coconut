package sk.qbsw.security.organization.spring.complex.iam.auth.base.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.organization.spring.complex.base.model.ComplexOrganization;
import sk.qbsw.security.organization.spring.complex.base.model.ComplexOrganizationLoggedUser;
import sk.qbsw.security.spring.iam.auth.common.model.IAMAuthData;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The IAM authentication logged user.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
public class CXOIAMAuthLoggedUser extends ComplexOrganizationLoggedUser
{
	private static final long serialVersionUID = 8407107405972233264L;

	@NotNull
	private IAMAuthData iamAuthData;

	/**
	 * Instantiates a new Logged iam auth user.
	 *
	 * @param id            the id
	 * @param username      the username
	 * @param password      the password
	 * @param authorities   the authorities
	 * @param userId        the user id
	 * @param organizations the organizations
	 * @param iamAuthData   the iam auth data
	 */
	public CXOIAMAuthLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, List<ComplexOrganization> organizations, IAMAuthData iamAuthData)
	{
		super(id, username, password, authorities, userId, organizations);
		this.iamAuthData = iamAuthData;
	}

	/**
	 * Instantiates a new O auth logged user.
	 *
	 * @param id                    the id
	 * @param username              the username
	 * @param password              the password
	 * @param authorities           the authorities
	 * @param userId                the user id
	 * @param organizations         the organizations
	 * @param iamAuthData           the iam auth data
	 * @param additionalInformation the additional information
	 */
	public CXOIAMAuthLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, List<ComplexOrganization> organizations, IAMAuthData iamAuthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, authorities, userId, organizations, additionalInformation);
		this.iamAuthData = iamAuthData;
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
