package sk.qbsw.security.spring.anonym.common.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.common.model.BaseAccountDetails;

import java.util.Collection;
import java.util.Map;

/**
 * The anonymous account.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.18.6
 */
public class AnonymousAccount extends BaseAccountDetails
{
	private static final long serialVersionUID = 2240342035990714647L;

	/**
	 * Instantiates a new Anonymous account.
	 *
	 * @param id the id
	 * @param authorities the authorities
	 */
	public AnonymousAccount (Long id, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, authorities);
	}

	/**
	 * Instantiates a new Anonymous account.
	 *
	 * @param id the id
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public AnonymousAccount (Long id, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, additionalInformation, authorities);
	}
}
