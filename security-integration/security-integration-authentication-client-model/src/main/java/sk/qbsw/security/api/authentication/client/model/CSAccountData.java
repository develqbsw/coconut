package sk.qbsw.security.api.authentication.client.model;

import io.swagger.annotations.ApiModelProperty;

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

	@ApiModelProperty (required = true, value = "The organization")
	@NotNull
	private CSSimplifiedOrganization organization;

	/**
	 * Instantiates a new Cs account data.
	 */
	public CSAccountData ()
	{
	}

	private CSAccountData (Builder builder)
	{
		setId(builder.id);
		setLogin(builder.login);
		setRoles(builder.roles);
		setOrganization(builder.organization);
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

	/**
	 * Gets organization.
	 *
	 * @return the organization
	 */
	public CSSimplifiedOrganization getOrganization ()
	{
		return organization;
	}

	/**
	 * Sets organization.
	 *
	 * @param organization the organization
	 */
	public void setOrganization (CSSimplifiedOrganization organization)
	{
		this.organization = organization;
	}


	/**
	 * The type Builder.
	 */
	public static final class Builder
	{
		private Long id;
		private String login;
		private List<String> roles;
		private CSSimplifiedOrganization organization;

		private Builder ()
		{
		}

		/**
		 * Id builder.
		 *
		 * @param val the val
		 * @return the builder
		 */
		public Builder id (Long val)
		{
			id = val;
			return this;
		}

		/**
		 * Login builder.
		 *
		 * @param val the val
		 * @return the builder
		 */
		public Builder login (String val)
		{
			login = val;
			return this;
		}

		/**
		 * Roles builder.
		 *
		 * @param val the val
		 * @return the builder
		 */
		public Builder roles (List<String> val)
		{
			roles = val;
			return this;
		}

		/**
		 * Organization builder.
		 *
		 * @param val the val
		 * @return the builder
		 */
		public Builder organization (CSSimplifiedOrganization val)
		{
			organization = val;
			return this;
		}

		/**
		 * Build cs account data.
		 *
		 * @return the cs account data
		 */
		public CSAccountData build ()
		{
			return new CSAccountData(this);
		}
	}
}
