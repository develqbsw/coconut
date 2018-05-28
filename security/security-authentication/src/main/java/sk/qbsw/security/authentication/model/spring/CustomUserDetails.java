package sk.qbsw.security.authentication.model.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import sk.qbsw.security.core.model.domain.Account;

/**
 * User details for spring Security.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.6.0
 */
public class CustomUserDetails implements UserDetails
{
	private static final long serialVersionUID = 1L;

	private Account user;

	@Override
	public Collection<GrantedAuthority> getAuthorities ()
	{
		List<String> roles = user.exportRoles();
		List<GrantedAuthority> retVal = new ArrayList<>();
		for (String role : roles)
		{
			retVal.add(new SimpleGrantedAuthority(role));
		}

		return retVal;
	}

	@Override
	public String getPassword ()
	{
		return user.getPasswordDigest();
	}

	@Override
	public String getUsername ()
	{
		return user.getLogin();
	}

	@Override
	public boolean isAccountNonExpired ()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked ()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired ()
	{
		return true;
	}

	@Override
	public boolean isEnabled ()
	{
		if (user.getFlagEnabled() == null)
		{
			return false;
		}
		else
		{
			return user.getFlagEnabled();
		}
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Account getUser ()
	{
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser (Account user)
	{
		this.user = user;
	}
}
