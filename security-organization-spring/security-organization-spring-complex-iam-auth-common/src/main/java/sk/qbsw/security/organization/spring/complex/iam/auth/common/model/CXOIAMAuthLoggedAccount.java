package sk.qbsw.security.organization.spring.complex.iam.auth.common.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.organization.spring.complex.common.model.CXOLoggedAccount;
import sk.qbsw.security.organization.spring.complex.common.model.CXOUserData;
import sk.qbsw.security.spring.iam.auth.common.model.IAMAuthData;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

/**
 * The IAM authentication logged user.
 *
 * @author Tomas Leken
 * @version 2.2.0
 * @since 1.19.0
 */
@Getter
public class CXOIAMAuthLoggedAccount extends CXOLoggedAccount
{
	private static final long serialVersionUID = 8407107405972233264L;

	@NotNull
	private IAMAuthData iamAuthData;

	/**
	 * Instantiates a new Cxoiam auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param user the user
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 */
	public CXOIAMAuthLoggedAccount (Long id, String username, String password, CXOUserData user, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData)
	{
		super(id, username, password, user, authorities);
		this.iamAuthData = iamAuthData;
	}

	/**
	 * Instantiates a new Cxoiam auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 * @param user the user
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 */
	public CXOIAMAuthLoggedAccount (Long id, String username, String password, boolean enabled, CXOUserData user, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData)
	{
		super(id, username, password, enabled, user, authorities);
		this.iamAuthData = iamAuthData;
	}

	/**
	 * Instantiates a new Cxoiam auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param user the user
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 * @param additionalInformation the additional information
	 */
	public CXOIAMAuthLoggedAccount (Long id, String username, String password, CXOUserData user, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, user, authorities, additionalInformation);
		this.iamAuthData = iamAuthData;
	}

	/**
	 * Instantiates a new Cxoiam auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 * @param user the user
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 * @param additionalInformation the additional information
	 */
	public CXOIAMAuthLoggedAccount (Long id, String username, String password, boolean enabled, CXOUserData user, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, enabled, user, authorities, additionalInformation);
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
