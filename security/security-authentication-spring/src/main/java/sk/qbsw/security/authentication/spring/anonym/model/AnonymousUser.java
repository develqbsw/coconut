package sk.qbsw.security.authentication.spring.anonym.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.authentication.spring.model.Organization;
import sk.qbsw.security.authentication.spring.model.SecurityUserDetailsBase;

import java.util.Collection;
import java.util.Map;

/**
 * The anonymous user.
 *
 * @author Tomas Lauro
 * @version 1.18.6
 * @since 1.18.6
 */
public class AnonymousUser extends SecurityUserDetailsBase
{
	private static final long serialVersionUID = 2240342035990714647L;

	/**
	 * Instantiates a new Anonymous user.
	 *
	 * @param id the id
	 * @param organization the organization
	 * @param authorities the authorities
	 */
	public AnonymousUser (Long id, Organization organization, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organization, authorities);
	}

	/**
	 * Instantiates a new Anonymous user.
	 *
	 * @param id the id
	 * @param organization the organization
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public AnonymousUser (Long id, Organization organization, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organization, additionalInformation, authorities);
	}
}
