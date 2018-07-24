package sk.qbsw.security.organization.spring.simple.system.base.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.organization.spring.simple.base.model.BaseSimpleOrganizationAccountDetails;
import sk.qbsw.security.organization.spring.simple.base.model.SimpleOrganization;

import java.util.Collection;
import java.util.Map;

/**
 * The system account.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.6
 */
public class SystemAccount extends BaseSimpleOrganizationAccountDetails
{
	private static final long serialVersionUID = 7009945322230986935L;

	/**
	 * Instantiates a new System account.
	 *
	 * @param id the id
	 * @param organization the organization
	 * @param authorities the authorities
	 */
	public SystemAccount (Long id, SimpleOrganization organization, Collection<? extends GrantedAuthority> authorities)
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
	public SystemAccount (Long id, SimpleOrganization organization, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organization, additionalInformation, authorities);
	}
}
