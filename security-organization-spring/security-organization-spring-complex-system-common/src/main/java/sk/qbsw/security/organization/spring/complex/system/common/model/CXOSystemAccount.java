package sk.qbsw.security.organization.spring.complex.system.common.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.organization.spring.complex.common.model.CXOBaseAccountDetails;
import sk.qbsw.security.organization.spring.complex.common.model.CXOUserData;

import java.util.Collection;
import java.util.Map;

/**
 * The complex organization system account.
 *
 * @author Tomas Leken
 * @version 2.1.0
 * @since 2.0.0
 */
public class CXOSystemAccount extends CXOBaseAccountDetails
{
	private static final long serialVersionUID = 5550099561472447922L;

	/**
	 * Instantiates a new Cxo system account.
	 *
	 * @param id the id
	 * @param user the user
	 * @param authorities the authorities
	 */
	public CXOSystemAccount (Long id, CXOUserData user, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, user, authorities);
	}

	/**
	 * Instantiates a new Cxo system account.
	 *
	 * @param id the id
	 * @param user the user
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public CXOSystemAccount (Long id, CXOUserData user, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		super(id, user, additionalInformation, authorities);
	}
}
