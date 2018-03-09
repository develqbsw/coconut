package sk.qbsw.security.authentication.spring.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * The security user details.
 *
 * @author Tomas Lauro
 * @version 1.18.4
 * @since 1.18.4
 */
public interface SecurityUserDetails extends Serializable
{
	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	Long getId();

	/**
	 * Gets organization.
	 *
	 * @return the organization
	 */
	Organization getOrganization();

	/**
	 * Gets additional information.
	 *
	 * @return the additional information
	 */
	Map<String, Object> getAdditionalInformation();

	/**
	 * Gets authorities.
	 *
	 * @return the authorities
	 */
	Collection<? extends GrantedAuthority> getAuthorities();
}
