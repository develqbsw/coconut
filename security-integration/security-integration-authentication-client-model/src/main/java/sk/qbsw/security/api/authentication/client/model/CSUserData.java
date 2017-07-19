package sk.qbsw.security.api.authentication.client.model;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sk.qbsw.core.client.model.BaseClientEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The user data.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class CSUserData extends BaseClientEntity
{
	private static final long serialVersionUID = 7790958331261348835L;

	@ApiModelProperty (required = true, value = "The user id")
	@NotNull
	private Long id;

	@ApiModelProperty (value = "The user name")
	private String name;

	@ApiModelProperty (value = "The user surname")
	private String surname;

	@ApiModelProperty (value = "The user email")
	private String email;

	@ApiModelProperty (required = true, value = "The user roles")
	@NotNull
	private List<String> roles;

	public CSUserData ()
	{
	}

	private CSUserData (Builder builder)
	{
		setId(builder.id);
		setName(builder.name);
		setSurname(builder.surname);
		setEmail(builder.email);
		setRoles(builder.roles);
	}

	/**
	 * New builder builder.
	 *
	 * @return the builder
	 */
	public static Builder newBuilder ()
	{
		return new Builder();
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public Long getId ()
	{
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId (Long id)
	{
		this.id = id;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName ()
	{
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName (String name)
	{
		this.name = name;
	}

	/**
	 * Gets surname.
	 *
	 * @return the surname
	 */
	public String getSurname ()
	{
		return surname;
	}

	/**
	 * Sets surname.
	 *
	 * @param surname the surname
	 */
	public void setSurname (String surname)
	{
		this.surname = surname;
	}

	/**
	 * Gets email.
	 *
	 * @return the email
	 */
	public String getEmail ()
	{
		return email;
	}

	/**
	 * Sets email.
	 *
	 * @param email the email
	 */
	public void setEmail (String email)
	{
		this.email = email;
	}

	/**
	 * Gets roles.
	 *
	 * @return the roles
	 */
	public List<String> getRoles ()
	{
		return roles;
	}

	/**
	 * Sets roles.
	 *
	 * @param roles the roles
	 */
	public void setRoles (List<String> roles)
	{
		this.roles = roles;
	}

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
		CSUserData castOther = (CSUserData) other;
		return new EqualsBuilder().append(id, castOther.id).append(name, castOther.name).append(surname, castOther.surname).append(email, castOther.email).append(roles, castOther.roles).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(-784399939, 542211991).append(id).append(name).append(surname).append(email).append(roles).toHashCode();
	}

	public static final class Builder
	{
		private Long id;
		private String name;
		private String surname;
		private String email;
		private List<String> roles;

		private Builder ()
		{
		}

		public Builder id (Long val)
		{
			id = val;
			return this;
		}

		public Builder name (String val)
		{
			name = val;
			return this;
		}

		public Builder surname (String val)
		{
			surname = val;
			return this;
		}

		public Builder email (String val)
		{
			email = val;
			return this;
		}

		public Builder roles (List<String> val)
		{
			roles = val;
			return this;
		}

		public CSUserData build ()
		{
			return new CSUserData(this);
		}
	}
}
