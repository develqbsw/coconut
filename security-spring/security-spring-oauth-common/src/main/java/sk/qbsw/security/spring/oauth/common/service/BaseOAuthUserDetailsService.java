package sk.qbsw.security.spring.oauth.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import sk.qbsw.security.spring.oauth.common.model.OAuthWebAuthenticationDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The oauth pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
public abstract class BaseOAuthUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>
{
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public final UserDetails loadUserDetails (PreAuthenticatedAuthenticationToken token) throws AuthenticationException
	{
		if (! (token.getDetails() instanceof OAuthWebAuthenticationDetails))
		{
			throw new BadCredentialsException("There are no expected OAuth token details");
		}

		return createUserDetails(token);
	}

	/**
	 * Create user details user details.
	 *
	 * @param token the token
	 * @return the user details
	 */
	protected abstract UserDetails createUserDetails (Authentication token);

	/**
	 * Convert roles to authorities collection.
	 *
	 * @param roles the roles
	 * @return the collection
	 */
	protected Collection<GrantedAuthority> convertRolesToAuthorities (List<String> roles)
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
