package sk.qbsw.security.organization.spring.simple.base.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.base.model.BaseAccountDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

/**
 * The base simple organization account details.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.6
 */
@Getter
public abstract class BaseSimpleOrganizationAccountDetails extends BaseAccountDetails implements SimpleOrganizationAccountDetails
{
	private static final long serialVersionUID = -4204231661652842220L;

	@NotNull
	private final SimpleOrganization organization;

	public BaseSimpleOrganizationAccountDetails (Long id, SimpleOrganization organization, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, authorities);
		this.organization = organization;
	}

	public BaseSimpleOrganizationAccountDetails (Long id, SimpleOrganization organization, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, additionalInformation, authorities);
		this.organization = organization;
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
