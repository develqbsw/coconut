package sk.qbsw.security.organization.spring.complex.anonym.base.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.organization.spring.complex.base.model.BaseComplexOrganizationAccountDetails;
import sk.qbsw.security.organization.spring.complex.base.model.ComplexOrganization;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The complex organization anonymous account.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
public class AnonymousAccount extends BaseComplexOrganizationAccountDetails
{

	/**
	 * Instantiates a new Simple organization anonymous account.
	 *
	 * @param id            the id
	 * @param organizations the organizations
	 * @param authorities   the authorities
	 */
	public AnonymousAccount (Long id, List<ComplexOrganization> organizations, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organizations, authorities);
	}

	/**
	 * Instantiates a new Simple organization anonymous account.
	 *
	 * @param id                    the id
	 * @param organizations         the organizations
	 * @param additionalInformation the additional information
	 * @param authorities           the authorities
	 */
	public AnonymousAccount (Long id, List<ComplexOrganization> organizations, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organizations, additionalInformation, authorities);
	}
}
