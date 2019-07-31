package sk.qbsw.security.spring.iam.auth.common.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.common.model.LoggedAccount;
import sk.qbsw.security.spring.common.model.UserData;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

/**
 * The IAM authentication logged account.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.0.0
 */
@Getter
public class IAMAuthLoggedAccount extends LoggedAccount
{
	private static final long serialVersionUID = 6001851088437587993L;

	@NotNull
	private IAMAuthData iamAuthData;

	/**
	 * Instantiates a new Iam auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 */
	public IAMAuthLoggedAccount (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData)
	{
		super(id, username, password, authorities);
		this.iamAuthData = iamAuthData;
	}

	/**
	 * Instantiates a new Iam auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 */
	public IAMAuthLoggedAccount (Long id, String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData)
	{
		super(id, username, password, enabled, authorities);
		this.iamAuthData = iamAuthData;
	}

	/**
	 * Instantiates a new Iam auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param user the user
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 */
	public IAMAuthLoggedAccount (Long id, String username, String password, UserData user, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData)
	{
		super(id, username, password, user, authorities);
		this.iamAuthData = iamAuthData;
	}

	/**
	 * Instantiates a new Iam auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 * @param user the user
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 */
	public IAMAuthLoggedAccount (Long id, String username, String password, boolean enabled, UserData user, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData)
	{
		super(id, username, password, enabled, user, authorities);
		this.iamAuthData = iamAuthData;
	}

	/**
	 * Instantiates a new Iam auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 * @param additionalInformation the additional information
	 */
	public IAMAuthLoggedAccount (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, authorities, additionalInformation);
		this.iamAuthData = iamAuthData;
	}

	/**
	 * Instantiates a new Iam auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 * @param additionalInformation the additional information
	 */
	public IAMAuthLoggedAccount (Long id, String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, enabled, authorities, additionalInformation);
		this.iamAuthData = iamAuthData;
	}

	/**
	 * Instantiates a new Iam auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param user the user
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 * @param additionalInformation the additional information
	 */
	public IAMAuthLoggedAccount (Long id, String username, String password, UserData user, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, user, authorities, additionalInformation);
		this.iamAuthData = iamAuthData;
	}

	/**
	 * Instantiates a new Iam auth logged account.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 * @param user the user
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 * @param additionalInformation the additional information
	 */
	public IAMAuthLoggedAccount (Long id, String username, String password, boolean enabled, UserData user, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, enabled, user, authorities, additionalInformation);
		this.iamAuthData = iamAuthData;
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
