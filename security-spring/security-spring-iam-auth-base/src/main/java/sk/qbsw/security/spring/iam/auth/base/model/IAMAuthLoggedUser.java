package sk.qbsw.security.spring.iam.auth.base.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.base.model.LoggedUser;
import sk.qbsw.security.spring.iam.auth.common.model.IAMAuthData;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

/**
 * The IAM authentication logged user.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
public class IAMAuthLoggedUser extends LoggedUser
{
	private static final long serialVersionUID = 6001851088437587993L;

	@NotNull
	private IAMAuthData iamAuthData;

	/**
	 * Instantiates a new Iam auth logged user.
	 *
	 * @param id          the id
	 * @param username    the username
	 * @param password    the password
	 * @param authorities the authorities
	 * @param iamAuthData the iam auth data
	 */
	public IAMAuthLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData)
	{
		super(id, username, password, authorities);
		this.iamAuthData = iamAuthData;
	}

	/**
	 * Instantiates a new Iam auth logged user.
	 *
	 * @param id                    the id
	 * @param username              the username
	 * @param password              the password
	 * @param authorities           the authorities
	 * @param iamAuthData           the iam auth data
	 * @param additionalInformation the additional information
	 */
	public IAMAuthLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, IAMAuthData iamAuthData, Map<String, Object> additionalInformation)
	{
		super(id, username, password, authorities, additionalInformation);
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
