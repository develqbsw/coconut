package sk.qbsw.security.authentication.spring.system.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.authentication.spring.model.Organization;
import sk.qbsw.security.authentication.spring.model.SecurityUserDetailsBase;

import java.util.Collection;
import java.util.Map;

/**
 * The system user.
 *
 * @author Tomas Lauro
 * @version 1.18.6
 * @since 1.18.6
 */
public class SystemUser extends SecurityUserDetailsBase
{
	private static final long serialVersionUID = -2868791358159661224L;

	/**
	 * Instantiates a new System user.
	 *
	 * @param id the id
	 * @param organization the organization
	 * @param authorities the authorities
	 */
	public SystemUser (Long id, Organization organization, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organization, authorities);
	}

	/**
	 * Instantiates a new System user.
	 *
	 * @param id the id
	 * @param organization the organization
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public SystemUser (Long id, Organization organization, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, organization, additionalInformation, authorities);
	}
}
