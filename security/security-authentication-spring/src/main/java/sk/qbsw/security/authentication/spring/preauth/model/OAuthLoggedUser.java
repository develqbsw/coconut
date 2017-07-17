package sk.qbsw.security.authentication.spring.preauth.model;

import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.authentication.spring.model.LoggedUser;
import sk.qbsw.security.authentication.spring.model.Organization;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * User details for spring Security.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.13.1
 * @since 1.6.0
 */
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
	 * @param organization the organization
	 * @param oauthData the oauth data
	 */
	public OAuthLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, Organization organization, OAuthData oauthData)
	{
		super(id, username, password, authorities, organization);
		this.oauthData = oauthData;
	}

	/**
	 * Gets oauth data.
	 *
	 * @return the oauth data
	 */
	public OAuthData getOauthData ()
	{
		return oauthData;
	}
}
