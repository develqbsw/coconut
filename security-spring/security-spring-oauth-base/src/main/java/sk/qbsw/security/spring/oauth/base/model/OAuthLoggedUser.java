package sk.qbsw.security.spring.oauth.base.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.base.model.LoggedUser;
import sk.qbsw.security.spring.oauth.common.model.OAuthData;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

/**
 * User details for spring Security.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.6.0
 */
@Getter
public class OAuthLoggedUser extends LoggedUser
{
	private static final long serialVersionUID = -4382555678527345919L;

	@NotNull
	private OAuthData oauthData;

	/**
	 * Instantiates a new Logged o auth user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param oauthData the oauth data
	 */
	public OAuthLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, OAuthData oauthData)
	{
		super(id, username, password, authorities);
		this.oauthData = oauthData;
	}

	/**
	 * Instantiates a new O auth logged user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param oauthData the oauth data
	 * @param additionalInformation the additional information
	 */
	public OAuthLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, OAuthData oauthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, authorities, additionalInformation);
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
