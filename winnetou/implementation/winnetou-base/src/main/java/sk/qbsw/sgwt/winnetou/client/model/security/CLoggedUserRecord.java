package sk.qbsw.sgwt.winnetou.client.model.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Record concerning logged user
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
@SuppressWarnings ("serial")
public class CLoggedUserRecord implements Serializable
{
	private String login;
	private String name;
	private String surname;
	private List<String> roles = new ArrayList<String>();
	private Long userId;

	public void addRole (String role)
	{
		this.roles.add(role);
	}

	/**
	 * Checks if user has entered role
	 * 
	 * @param roleToCheck role to check
	 * @return true if user has the role
	 */
	public boolean containsRole (String roleToCheck)
	{
		return roles.contains(roleToCheck);
	}

	/**
	 * Checks if the user has at least one role from entered list
	 * @param roleToCheck roles to check
	 * @return true if user has at least one role
	 */
	public boolean containsRole (String... roleToCheck)
	{
		for (String role : roleToCheck)
		{
			if (roles.contains(role))
			{
				return true;
			}
		}

		return false;
	}

	public String getLogin ()
	{
		return login;
	}

	public String getName ()
	{
		return name;
	}

	public List<String> getRoles ()
	{
		return roles;
	}

	public String getSurname ()
	{
		return surname;
	}

	public Long getUserId ()
	{
		return userId;
	}

	public void setLogin (String login)
	{
		this.login = login;
	}

	public void setName (String name)
	{
		this.name = name;
	}

	public void setRoles (List<String> role)
	{
		this.roles = role;
	}

	public void setSurname (String surname)
	{
		this.surname = surname;
	}

	public void setUserId (Long userId)
	{
		this.userId = userId;
	}
}
