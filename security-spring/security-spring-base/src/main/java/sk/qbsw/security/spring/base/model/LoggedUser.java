package sk.qbsw.security.spring.base.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The logged user.
 *
 * @author Tomas Lauro
 * @version 1.18.4
 * @since 1.18.0
 */
@Getter
public class LoggedUser extends User implements AccountDetails
{
	private static final long serialVersionUID = -8482140113909737224L;

	@NotNull
	private Long id;

	@NotNull
	private Organization organization;

	@NotNull
	private Map<String, Object> additionalInformation = new HashMap<>();

	/**
	 * Instantiates a new Logged user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param organization the organization
	 */
	public LoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, Organization organization)
	{
		super(username, password, authorities);
		this.id = id;
		this.organization = organization;
	}

	/**
	 * Instantiates a new Logged user.
	 *
	 * @param id the id
	 * @param username the username
	 * @param password the password
	 * @param authorities the authorities
	 * @param organization the organization
	 * @param additionalInformation the additional information
	 */
	public LoggedUser (Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, Organization organization, Map<String, Object> additionalInformation)
	{
		super(username, password, authorities);
		this.id = id;
		this.organization = organization;
		this.additionalInformation = additionalInformation;
	}

	/**
	 * Compares objects based on login
	 * 
	 * @param rhs object to compare
	 * @return true / false
	 */
	@Override
	public boolean equals (Object rhs)
	{
		return super.equals(rhs);
	}

	/**
	 * Generates hashcode from login
	 * 
	 * @return hashcode from login
	 */
	@Override
	public int hashCode ()
	{
		return super.hashCode();
	}
}
