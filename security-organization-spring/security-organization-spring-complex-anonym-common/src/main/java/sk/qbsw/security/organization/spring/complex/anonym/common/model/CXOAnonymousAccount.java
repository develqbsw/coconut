package sk.qbsw.security.organization.spring.complex.anonym.common.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.organization.spring.complex.common.model.CXOBaseAccountDetails;
import sk.qbsw.security.organization.spring.complex.common.model.CXOUserData;

import java.util.Collection;
import java.util.Map;

/**
 * The complex organization anonymous account.
 *
 * @author Tomas Leken
 * @version 2.1.0
 * @since 2.0.0
 */
public class CXOAnonymousAccount extends CXOBaseAccountDetails
{
	/**
	 * Instantiates a new Cxo anonymous account.
	 *
	 * @param id the id
	 * @param user the user
	 * @param authorities the authorities
	 */
	public CXOAnonymousAccount (Long id, CXOUserData user, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, user, authorities);
	}

	/**
	 * Instantiates a new Cxo anonymous account.
	 *
	 * @param id the id
	 * @param user the user
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public CXOAnonymousAccount (Long id, CXOUserData user, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, user, additionalInformation, authorities);
	}
}
