package sk.qbsw.security.spring.iam.auth.base.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.base.model.LoggedUser;

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
	private String uid;

	@NotNull
	private String token;

	/**
	 * Instantiates a new Iam auth logged user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param uid the uid
	 * @param token the token
	 */
	public IAMAuthLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, String uid, String token)
	{
		super(id, username, password, authorities);
		this.uid = uid;
		this.token = token;
	}

	/**
	 * Instantiates a new Iam auth logged user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param uid the uid
	 * @param token the token
	 * @param additionalInformation the additional information
	 */
	public IAMAuthLoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, String uid, String token, Map<String, Object> additionalInformation)
	{
		super(id, username, password, authorities, additionalInformation);
		this.uid = uid;
		this.token = token;
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
