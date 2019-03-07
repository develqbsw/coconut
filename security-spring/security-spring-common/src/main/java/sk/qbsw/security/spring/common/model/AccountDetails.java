package sk.qbsw.security.spring.common.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * The security account details.
 *
 * @author Tomas Lauro
 * @version 2.1.0
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
	 * Gets user.
	 *
	 * @return the user
	 */
	UserData getUser ();

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
