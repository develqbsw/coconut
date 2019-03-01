package sk.qbsw.security.spring.common.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The authority converter.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.18.0
 */
public class AuthorityConverterImpl implements AuthorityConverter
{
	@Override
	public Collection<GrantedAuthority> convertRolesToAuthorities (List<String> roles)
	{
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		if (roles != null)
		{
			for (String role : roles)
			{
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
			}
		}

		return grantedAuthorities;
	}
}
