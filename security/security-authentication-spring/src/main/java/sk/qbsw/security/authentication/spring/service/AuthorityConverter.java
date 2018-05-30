package sk.qbsw.security.authentication.spring.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * The authority converter.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.4
 */
public interface AuthorityConverter
{
	/**
	 * Convert roles to authorities collection.
	 *
	 * @param roles the roles
	 * @return the collection
	 */
	Collection<GrantedAuthority> convertRolesToAuthorities (List<String> roles);
}
