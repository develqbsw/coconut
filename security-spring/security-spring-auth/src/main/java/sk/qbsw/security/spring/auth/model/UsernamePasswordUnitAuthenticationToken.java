package sk.qbsw.security.spring.auth.model;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * The username password and unit token..
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
@Getter
public class UsernamePasswordUnitAuthenticationToken extends UsernamePasswordAuthenticationToken
{
	private static final long serialVersionUID = 1L;

	private String unit;

	/**
	 * Instantiates a new c username password unit authentication token.
	 *
	 * @param principal the principal
	 * @param credentials the credentials
	 * @param authorities the authorities
	 * @param unit the unit
	 */
	public UsernamePasswordUnitAuthenticationToken (Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String unit)
	{
		super(principal, credentials, authorities);
		this.unit = unit;
	}

	/**
	 * Instantiates a new c username password unit authentication token.
	 *
	 * @param principal the principal
	 * @param credentials the credentials
	 * @param unit the unit
	 */
	public UsernamePasswordUnitAuthenticationToken (String principal, String credentials, String unit)
	{
		super(principal, credentials);

		this.unit = unit;
	}
}
