package sk.qbsw.security.organization.spring.complex.base.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.base.model.BaseAccountDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The base complex organization account details.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
public abstract class BaseComplexOrganizationAccountDetails extends BaseAccountDetails implements ComplexOrganizationAccountDetails
{
	private static final long serialVersionUID = -4204231661652842220L;

	@NotNull
	private final List<ComplexOrganization> organizations;

	/**
	 * Instantiates a new Base complex organization account details.
	 *
	 * @param id the id
	 * @param organizations the organizations
	 * @param authorities the authorities
	 */
	public BaseComplexOrganizationAccountDetails (Long id, List<ComplexOrganization> organizations, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, authorities);
		this.organizations = organizations;
	}

	/**
	 * Instantiates a new Base complex organization account details.
	 *
	 * @param id the id
	 * @param organizations the organizations
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public BaseComplexOrganizationAccountDetails (Long id, List<ComplexOrganization> organizations, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, additionalInformation, authorities);
		this.organizations = organizations;
	}

	@Override
	public boolean equals (Object rhs)
	{
		return super.equals(rhs);
	}

	@Override
	public int hashCode ()
	{
		return super.hashCode();
	}
}
