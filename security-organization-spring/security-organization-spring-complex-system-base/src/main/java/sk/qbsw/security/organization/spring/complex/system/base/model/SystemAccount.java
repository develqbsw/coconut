package sk.qbsw.security.organization.spring.complex.system.base.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.organization.spring.complex.base.model.BaseComplexOrganizationAccountDetails;
import sk.qbsw.security.organization.spring.complex.base.model.ComplexOrganization;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The type System account.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
public class SystemAccount extends BaseComplexOrganizationAccountDetails
{
	private static final long serialVersionUID = 5550099561472447922L;

	/**
	 * Instantiates a new System account.
	 *
	 * @param id            the id
	 * @param organizations the organizations
	 * @param authorities   the authorities
	 */
	public SystemAccount (Long id, List<ComplexOrganization> organizations, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organizations, authorities);
	}

	/**
	 * Instantiates a new System account.
	 *
	 * @param id                    the id
	 * @param organizations         the organizations
	 * @param additionalInformation the additional information
	 * @param authorities           the authorities
	 */
	public SystemAccount (Long id, List<ComplexOrganization> organizations, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organizations, additionalInformation, authorities);
	}
}
