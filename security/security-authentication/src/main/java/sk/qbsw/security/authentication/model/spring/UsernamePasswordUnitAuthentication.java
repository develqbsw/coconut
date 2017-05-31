package sk.qbsw.security.authentication.model.spring;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * CustomUsernamePasswordUnitAuthenticationToken.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.6.0
 */
public class UsernamePasswordUnitAuthentication extends UsernamePasswordAuthenticationToken
{
	/** Default serialization version. */
	private static final long serialVersionUID = 1L;

	/** The unit. */
	private String unit;

	/**
	 * Instantiates a new c username password unit authentication token.
	 *
	 * @param principal the principal
	 * @param credentials the credentials
	 * @param authorities the authorities
	 */
	public UsernamePasswordUnitAuthentication (Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String unit)
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
	public UsernamePasswordUnitAuthentication (String principal, String credentials, String unit)
	{
		super(principal, credentials);

		this.unit = unit;
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public String getUnit ()
	{
		return unit;
	}
}
