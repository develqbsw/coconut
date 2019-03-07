package sk.qbsw.security.organization.spring.simple.common.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.common.model.AccountDetails;
import sk.qbsw.security.spring.common.model.BaseAccountDetails;

import java.util.Collection;
import java.util.Map;

/**
 * The base simple organization account details.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.18.6
 */
@Getter
public abstract class SPOBaseAccountDetails extends BaseAccountDetails implements AccountDetails
{
	private static final long serialVersionUID = -4204231661652842220L;

	/**
	 * Instantiates a new Spo base account details.
	 *
	 * @param id the id
	 * @param user the user
	 * @param authorities the authorities
	 */
	public SPOBaseAccountDetails (Long id, SPOUserData user, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, user, authorities);
	}

	/**
	 * Instantiates a new Spo base account details.
	 *
	 * @param id the id
	 * @param user the user
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public SPOBaseAccountDetails (Long id, SPOUserData user, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, user, additionalInformation, authorities);
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
