package sk.qbsw.security.api.authentication.client.model;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * The public client side account data.
 *
 * @author Jana Branisova
 * @version 1.18.0
 * @since 1.18.0
 */
public class CSAccountData implements Serializable
{
	private static final long serialVersionUID = 5194693663926536012L;

	@ApiModelProperty (required = true, value = "The identifier")
	@NotNull
	private Long id;

	@ApiModelProperty (required = true, value = "The login")
	@NotNull
	private String login;

	@ApiModelProperty (required = true, value = "The user roles")
	@NotNull
	private List<String> roles;

	public CSAccountData ()
	{
	}

	private CSAccountData (Builder builder)
	{
		setId(builder.id);
		setLogin(builder.login);
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
	 * Gets login.
	 *
	 * @return the login
	 */
	public String getLogin ()
	{
		return login;
	}

	/**
	 * Sets login.
	 *
	 * @param login the login
	 */
	public void setLogin (String login)
	{
		this.login = login;
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
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		CSAccountData that = (CSAccountData) o;

		return new EqualsBuilder().append(id, that.id).append(login, that.login).append(roles, that.roles).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).append(id).append(login).append(roles).toHashCode();
	}

	public static final class Builder
	{
		private Long id;
		private String login;
		private List<String> roles;

		private Builder ()
		{
		}

		public Builder id (Long val)
		{
			id = val;
			return this;
		}

		public Builder login (String val)
		{
			login = val;
			return this;
		}

		public Builder roles (List<String> val)
		{
			roles = val;
			return this;
		}

		public CSAccountData build ()
		{
			return new CSAccountData(this);
		}
	}
}
