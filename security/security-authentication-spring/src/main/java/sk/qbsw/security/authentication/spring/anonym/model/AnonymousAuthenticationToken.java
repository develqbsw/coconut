package sk.qbsw.security.authentication.spring.anonym.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * The anonymous authentication token.
 *
 * @author Tomas Lauro
 * @version 1.18.4
 * @since 1.18.4
 */
public class AnonymousAuthenticationToken extends AbstractAuthenticationToken
{
	private static final long serialVersionUID = -4712191244185087875L;
	private final String key; // backward compatibility with anonymous authentication token
	private final Object principal;

	/**
	 * Instantiates a new anonymous authentication token.
	 *
	 * @param key the key
	 * @param principal the principal
	 */
	public AnonymousAuthenticationToken (String key, Object principal)
	{
		this(key, principal, null);
		super.setAuthenticated(false);
	}

	/**
	 * Instantiates a new anonymous authentication token.
	 *
	 * @param key the key
	 * @param principal the principal
	 * @param authorities the authorities
	 */
	public AnonymousAuthenticationToken (String key, Object principal, Collection<? extends GrantedAuthority> authorities)
	{
		super(authorities);
		if (key == null || "".equals(key) || principal == null || "".equals(principal))
		{
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		this.key = key;
		this.principal = principal;
		super.setAuthenticated(true);
	}

	/**
	 * Gets key.
	 *
	 * @return the key
	 */
	public String getKey ()
	{
		return this.key;
	}

	public Object getPrincipal ()
	{
		return this.principal;
	}

	public Object getCredentials ()
	{
		return "";
	}

	public void setAuthenticated (boolean isAuthenticated)
	{
		if (isAuthenticated)
		{
			throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}
}
