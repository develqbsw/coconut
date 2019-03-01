package sk.qbsw.security.organization.spring.complex.common.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.common.model.AccountDetails;
import sk.qbsw.security.spring.common.model.LoggedAccount;

import java.util.Collection;
import java.util.Map;

/**
 * The complex organization logged account.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.0.0
 */
@Getter
public class CXOLoggedAccount extends LoggedAccount implements AccountDetails
{
	private static final long serialVersionUID = -198736486895065544L;

	/**
	 * Instantiates a new Cxo logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param user the user
	 * @param authorities the authorities
	 */
	public CXOLoggedAccount (Long id, String username, String password, CXOUserData user, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, username, password, user, authorities);
	}

	/**
	 * Instantiates a new Cxo logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param user the user
	 * @param authorities the authorities
	 * @param additionalInformation the additional information
	 */
	public CXOLoggedAccount (Long id, String username, String password, CXOUserData user, Collection<? extends GrantedAuthority> authorities, Map<String, Object> additionalInformation)
	{
		super(id, username, password, user, authorities, additionalInformation);
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
