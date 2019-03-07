package sk.qbsw.security.organization.spring.simple.common.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.common.model.LoggedAccount;

import java.util.Collection;
import java.util.Map;

/**
 * The simple organization logged account.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.18.0
 */
@Getter
public class SPOLoggedAccount extends LoggedAccount
{
	private static final long serialVersionUID = -198736486895065544L;

	/**
	 * Instantiates a new Spo logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param user the user
	 * @param authorities the authorities
	 */
	public SPOLoggedAccount (Long id, String username, String password, SPOUserData user, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, username, password, user, authorities);
	}

	/**
	 * Instantiates a new Spo logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param user the user
	 * @param authorities the authorities
	 * @param additionalInformation the additional information
	 */
	public SPOLoggedAccount (Long id, String username, String password, SPOUserData user, Collection<? extends GrantedAuthority> authorities, Map<String, Object> additionalInformation)
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
