package sk.qbsw.security.authentication.spring.system.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.authentication.spring.model.Organization;
import sk.qbsw.security.authentication.spring.model.AccountDetailsBase;

import java.util.Collection;
import java.util.Map;

/**
 * The system account.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.6
 */
public class SystemAccount extends AccountDetailsBase
{
	private static final long serialVersionUID = -2868791358159661224L;

	/**
	 * Instantiates a new System account.
	 *
	 * @param id the id
	 * @param organization the organization
	 * @param authorities the authorities
	 */
	public SystemAccount (Long id, Organization organization, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organization, authorities);
	}

	/**
	 * Instantiates a new System account.
	 *
	 * @param id the id
	 * @param organization the organization
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public SystemAccount (Long id, Organization organization, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organization, additionalInformation, authorities);
	}
}
