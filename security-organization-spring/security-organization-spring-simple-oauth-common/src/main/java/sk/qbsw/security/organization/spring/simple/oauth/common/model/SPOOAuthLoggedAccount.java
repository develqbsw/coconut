package sk.qbsw.security.organization.spring.simple.oauth.common.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.organization.spring.simple.common.model.SPOLoggedAccount;
import sk.qbsw.security.organization.spring.simple.common.model.SPOUserData;
import sk.qbsw.security.spring.oauth.common.model.OAuthData;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

/**
 * User details for spring Security.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.6.0
 */
@Getter
public class SPOOAuthLoggedAccount extends SPOLoggedAccount
{
	private static final long serialVersionUID = -4382555678527345919L;

	@NotNull
	private OAuthData oauthData;

	/**
	 * Instantiates a new Spoo auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param user the user
	 * @param authorities the authorities
	 * @param oauthData the oauth data
	 */
	public SPOOAuthLoggedAccount (Long id, String username, String password, SPOUserData user, Collection<? extends GrantedAuthority> authorities, OAuthData oauthData)
	{
		super(id, username, password, user, authorities);
		this.oauthData = oauthData;
	}

	/**
	 * Instantiates a new Spoo auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param user the user
	 * @param authorities the authorities
	 * @param oauthData the oauth data
	 * @param additionalInformation the additional information
	 */
	public SPOOAuthLoggedAccount (Long id, String username, String password, SPOUserData user, Collection<? extends GrantedAuthority> authorities, OAuthData oauthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, user, authorities, additionalInformation);
		this.oauthData = oauthData;
	}

	@Override
	public boolean equals (Object rhs)
	{
		return super.equals(rhs);
	}

	@Override
	public int hashCode ()
	{
		return super.hashCode();
	}

}
