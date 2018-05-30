package sk.qbsw.security.authentication.spring.anonym.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.authentication.spring.model.Organization;
import sk.qbsw.security.authentication.spring.model.AccountDetailsBase;

import java.util.Collection;
import java.util.Map;

/**
 * The anonymous account.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.6
 */
public class AnonymousAccount extends AccountDetailsBase
{
	private static final long serialVersionUID = 2240342035990714647L;

	/**
	 * Instantiates a new Anonymous account.
	 *
	 * @param id the id
	 * @param organization the organization
	 * @param authorities the authorities
	 */
	public AnonymousAccount (Long id, Organization organization, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organization, authorities);
	}

	/**
	 * Instantiates a new Anonymous account.
	 *
	 * @param id the id
	 * @param organization the organization
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public AnonymousAccount (Long id, Organization organization, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organization, additionalInformation, authorities);
	}
}
