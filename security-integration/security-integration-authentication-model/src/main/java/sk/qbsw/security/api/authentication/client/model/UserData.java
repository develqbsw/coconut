package sk.qbsw.security.api.authentication.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import io.swagger.annotations.ApiModelProperty;
import sk.qbsw.core.api.model.BaseClientEntity;

/**
 * The user data.
 *
 * @author  Tomas Lauro
 * @version 1.18.0
 * @since   1.18.0
 */
public class UserData extends BaseClientEntity
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7790958331261348835L;

	/** The id. */
	@ApiModelProperty (required = true, value = "The user id")
	@NotNull
	private Long id;

	/** The name. */
	@ApiModelProperty (required = false, value = "The user name")
	private String name;

	/** The surname. */
	@ApiModelProperty (required = false, value = "The user surname")
	private String surname;

	/** The email. */
	@ApiModelProperty (required = false, value = "The user email")
	private String email;

	/** The roles. */
	@ApiModelProperty (required = true, value = "The user roles")
	@NotNull
	private List<String> roles = new ArrayList<>();

	/**
	 * Id.
	 *
	 * @param id the id
	 * @return the ICS user data
	 */
	public UserData id (Long id)
	{
		this.id = id;
		return this;
	}

	/**
	 * Name.
	 *
	 * @param name the name
	 * @return the ICS user data
	 */
	public UserData name (String name)
	{
		this.name = name;
		return this;
	}

	/**
	 * Surname.
	 *
	 * @param surname the surname
	 * @return the ICS user data
	 */
	public UserData surname (String surname)
	{
		this.surname = surname;
		return this;
	}

	/**
	 * Email.
	 *
	 * @param email the email
	 * @return the ICS user data
	 */
	public UserData email (String email)
	{
		this.email = email;
		return this;
	}

	/**
	 * Roles.
	 *
	 * @param roles the roles
	 * @return the ICS user data
	 */
	public UserData roles (List<String> roles)
	{
		this.roles = roles;
		return this;
	}

	/**
	 * Adds the roles item.
	 *
	 * @param rolesItem the roles item
	 * @return the ICS user data
	 */
	public UserData addRolesItem (String rolesItem)
	{
		this.roles.add(rolesItem);
		return this;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId ()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId (Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName ()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName (String name)
	{
		this.name = name;
	}

	/**
	 * Gets the surname.
	 *
	 * @return the surname
	 */
	public String getSurname ()
	{
		return surname;
	}

	/**
	 * Sets the surname.
	 *
	 * @param surname the new surname
	 */
	public void setSurname (String surname)
	{
		this.surname = surname;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail ()
	{
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail (String email)
	{
		this.email = email;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public List<String> getRoles ()
	{
		return roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles the new roles
	 */
	public void setRoles (List<String> roles)
	{
		this.roles = roles;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (final Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (other == null)
		{
			return false;
		}
		if (!getClass().equals(other.getClass()))
		{
			return false;
		}
		UserData castOther = (UserData) other;
		return new EqualsBuilder().append(id, castOther.id).append(name, castOther.name).append(surname, castOther.surname).append(email, castOther.email).append(roles, castOther.roles).isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(-784399939, 542211991).append(id).append(name).append(surname).append(email).append(roles).toHashCode();
	}
}
