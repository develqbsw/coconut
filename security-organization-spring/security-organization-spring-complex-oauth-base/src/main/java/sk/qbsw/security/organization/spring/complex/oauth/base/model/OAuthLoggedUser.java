package sk.qbsw.security.organization.spring.complex.oauth.base.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.organization.spring.complex.base.model.ComplexOrganization;
import sk.qbsw.security.organization.spring.complex.base.model.ComplexOrganizationLoggedUser;
import sk.qbsw.security.spring.oauth.common.model.OAuthData;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User details for spring Security.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
public class OAuthLoggedUser extends ComplexOrganizationLoggedUser
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
	 * @param userId the user id
	 * @param organizations the organizations
	 * @param oauthData the oauth data
	 */
	public OAuthLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, List<ComplexOrganization> organizations, OAuthData oauthData)
	{
		super(id, username, password, authorities, userId, organizations);
		this.oauthData = oauthData;
	}

	/**
	 * Instantiates a new O auth logged user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param userId the user id
	 * @param organizations the organizations
	 * @param oauthData the oauth data
	 * @param additionalInformation the additional information
	 */
	public OAuthLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, List<ComplexOrganization> organizations, OAuthData oauthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, authorities, userId, organizations, additionalInformation);
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
