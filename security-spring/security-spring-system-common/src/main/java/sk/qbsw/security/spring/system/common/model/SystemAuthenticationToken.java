package sk.qbsw.security.spring.system.common.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * The system account authentication token.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.6
 */
public class SystemAuthenticationToken extends AbstractAuthenticationToken implements Serializable
{
	private static final long serialVersionUID = 5131609532943576306L;

	private static final String DEFAULT_PRINCIPAL = "systemAuthenticationTokenPrincipal";

	private final Object principal;

	/**
	 * Instantiates a new System authentication token.
	 */
	public SystemAuthenticationToken ()
	{
		super(null);
		this.principal = DEFAULT_PRINCIPAL;
		setAuthenticated(false);
	}

	/**
	 * Instantiates a new System authentication token.
	 *
	 * @param principal the principal
	 * @param authorities the authorities
	 */
	public SystemAuthenticationToken (Object principal, Collection<? extends GrantedAuthority> authorities)
	{
		super(authorities);

		if (principal == null || "".equals(principal) || authorities == null || (authorities.isEmpty()))
		{
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		this.principal = principal;
		setAuthenticated(true);
	}

	public Object getPrincipal ()
	{
		return this.principal;
	}

	public Object getCredentials ()
	{
		return "";
	}
}
