package sk.qbsw.security.organization.spring.simple.anonym.common.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.organization.spring.simple.common.model.SPOBaseAccountDetails;
import sk.qbsw.security.organization.spring.simple.common.model.SPOUserData;

import java.util.Collection;
import java.util.Map;

/**
 * The simple organization anonymous account.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.18.6
 */
public class SPOAnonymousAccount extends SPOBaseAccountDetails
{
	private static final long serialVersionUID = -573860262804408811L;

	/**
	 * Instantiates a new Spo anonymous account.
	 *
	 * @param id the id
	 * @param user the user
	 * @param authorities the authorities
	 */
	public SPOAnonymousAccount (Long id, SPOUserData user, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, user, authorities);
	}

	/**
	 * Instantiates a new Spo anonymous account.
	 *
	 * @param id the id
	 * @param user the user
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public SPOAnonymousAccount (Long id, SPOUserData user, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, user, additionalInformation, authorities);
	}
}
