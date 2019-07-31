package sk.qbsw.security.spring.oauth.common.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.common.model.LoggedAccount;
import sk.qbsw.security.spring.common.model.UserData;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

/**
 * User details for spring Security.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.6.0
 */
@Getter
public class OAuthLoggedAccount extends LoggedAccount
{
	private static final long serialVersionUID = -4382555678527345919L;

	@NotNull
	private OAuthData oauthData;

	/**
	 * Instantiates a new O auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param oauthData the oauth data
	 */
	public OAuthLoggedAccount (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, OAuthData oauthData)
	{
		super(id, username, password, authorities);
		this.oauthData = oauthData;
	}

	/**
	 * Instantiates a new O auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 * @param authorities the authorities
	 * @param oauthData the oauth data
	 */
	public OAuthLoggedAccount (Long id, String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities, OAuthData oauthData)
	{
		super(id, username, password, enabled, authorities);
		this.oauthData = oauthData;
	}

	/**
	 * Instantiates a new O auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param user the user
	 * @param authorities the authorities
	 * @param oauthData the oauth data
	 */
	public OAuthLoggedAccount (Long id, String username, String password, UserData user, Collection<? extends GrantedAuthority> authorities, OAuthData oauthData)
	{
		super(id, username, password, user, authorities);
		this.oauthData = oauthData;
	}

	/**
	 * Instantiates a new O auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 * @param user the user
	 * @param authorities the authorities
	 * @param oauthData the oauth data
	 */
	public OAuthLoggedAccount (Long id, String username, String password, boolean enabled, UserData user, Collection<? extends GrantedAuthority> authorities, OAuthData oauthData)
	{
		super(id, username, password, enabled, user, authorities);
		this.oauthData = oauthData;
	}

	/**
	 * Instantiates a new O auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param oauthData the oauth data
	 * @param additionalInformation the additional information
	 */
	public OAuthLoggedAccount (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, OAuthData oauthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, authorities, additionalInformation);
		this.oauthData = oauthData;
	}

	/**
	 * Instantiates a new O auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 * @param authorities the authorities
	 * @param oauthData the oauth data
	 * @param additionalInformation the additional information
	 */
	public OAuthLoggedAccount (Long id, String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities, OAuthData oauthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, enabled, authorities, additionalInformation);
		this.oauthData = oauthData;
	}

	/**
	 * Instantiates a new O auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param user the user
	 * @param authorities the authorities
	 * @param oauthData the oauth data
	 * @param additionalInformation the additional information
	 */
	public OAuthLoggedAccount (Long id, String username, String password, UserData user, Collection<? extends GrantedAuthority> authorities, OAuthData oauthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, user, authorities, additionalInformation);
		this.oauthData = oauthData;
	}

	/**
	 * Instantiates a new O auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 * @param user the user
	 * @param authorities the authorities
	 * @param oauthData the oauth data
	 * @param additionalInformation the additional information
	 */
	public OAuthLoggedAccount (Long id, String username, String password, boolean enabled, UserData user, Collection<? extends GrantedAuthority> authorities, OAuthData oauthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, enabled, user, authorities, additionalInformation);
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
