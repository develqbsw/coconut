package sk.qbsw.security.spring.system.base.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.base.model.BaseAccountDetails;

import java.util.Collection;
import java.util.Map;

/**
 * The system account.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.6
 */
public class SystemAccount extends BaseAccountDetails
{
	private static final long serialVersionUID = -2868791358159661224L;

	/**
	 * Instantiates a new System account.
	 *
	 * @param id the id
	 * @param authorities the authorities
	 */
	public SystemAccount (Long id, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, authorities);
	}

	/**
	 * Instantiates a new System account.
	 *
	 * @param id the id
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public SystemAccount (Long id, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, additionalInformation, authorities);
	}
}
