package sk.qbsw.security.spring.anonym.common.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * The anonymous authentication token.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.4
 */
public class AnonymousAuthenticationToken extends org.springframework.security.authentication.AnonymousAuthenticationToken
{
	private static final long serialVersionUID = -4712191244185087875L;

	/**
	 * Instantiates a new anonymous authentication token.
	 *
	 * @param key the key
	 * @param principal the principal
	 * @param authorities the authorities
	 * @param authenticated the authenticated
	 */
	public AnonymousAuthenticationToken (String key, Object principal, Collection<? extends GrantedAuthority> authorities, boolean authenticated)
	{
		super(key, principal, authorities);
		super.setAuthenticated(authenticated);
	}
}
