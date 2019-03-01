package sk.qbsw.security.organization.spring.simple.system.common.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.organization.spring.simple.common.model.SPOBaseAccountDetails;
import sk.qbsw.security.organization.spring.simple.common.model.SPOUserData;

import java.util.Collection;
import java.util.Map;

/**
 * The system account.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.18.6
 */
public class SPOSystemAccount extends SPOBaseAccountDetails
{
	private static final long serialVersionUID = 7009945322230986935L;

	/**
	 * Instantiates a new Spo system account.
	 *
	 * @param id the id
	 * @param user the user
	 * @param authorities the authorities
	 */
	public SPOSystemAccount (Long id, SPOUserData user, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, user, authorities);
	}

	/**
	 * Instantiates a new Spo system account.
	 *
	 * @param id the id
	 * @param user the user
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public SPOSystemAccount (Long id, SPOUserData user, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, user, additionalInformation, authorities);
	}
}
