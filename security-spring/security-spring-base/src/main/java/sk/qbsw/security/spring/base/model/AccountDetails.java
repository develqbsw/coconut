package sk.qbsw.security.spring.base.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * The security account details.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.4
 */
public interface AccountDetails extends Serializable
{
	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	Long getId ();

	/**
	 * Gets additional information.
	 *
	 * @return the additional information
	 */
	Map<String, Object> getAdditionalInformation ();

	/**
	 * Gets authorities.
	 *
	 * @return the authorities
	 */
	Collection<? extends GrantedAuthority> getAuthorities ();
}
