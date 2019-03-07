package sk.qbsw.security.spring.common.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * The authority converter.
 *
 * @author Tomas Lauro
 * @version 2.1.0
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
