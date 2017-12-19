package sk.qbsw.security.api.authentication.client.model;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sk.qbsw.core.client.model.BaseClientEntity;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@ApiModelProperty (required = true, value = "The organization")
	@NotNull
	private CSSimplifiedOrganization organization;

	@ApiModelProperty (value = "The additional custom user information")
	private Map<String, Object> additionalInformation = new HashMap<>();

	/**
	 * Instantiates a new Cs user data.
	 */
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
		setOrganization(builder.organization);
		setAdditionalInformation(builder.additionalInformation);
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
	 * Gets additional information.
	 *
	 * @return the additional information
	 */
	public Map<String, Object> getAdditionalInformation ()
	{
		return additionalInformation;
	}

	/**
	 * Sets additional information.
	 *
	 * @param additionalInformation the additional information
	 */
	public void setAdditionalInformation (Map<String, Object> additionalInformation)
	{
		this.additionalInformation = additionalInformation;
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

	/**
	 * The type Builder.
	 */
	public static final class Builder
	{
		private Long id;
		private String name;
		private String surname;
		private String email;
		private List<String> roles;
		private CSSimplifiedOrganization organization;
		private Map<String, Object> additionalInformation;

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
		 * Name builder.
		 *
		 * @param val the val
		 * @return the builder
		 */
		public Builder name (String val)
		{
			name = val;
			return this;
		}

		/**
		 * Surname builder.
		 *
		 * @param val the val
		 * @return the builder
		 */
		public Builder surname (String val)
		{
			surname = val;
			return this;
		}

		/**
		 * Email builder.
		 *
		 * @param val the val
		 * @return the builder
		 */
		public Builder email (String val)
		{
			email = val;
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
		 * Additional information builder.
		 *
		 * @param val the val
		 * @return the builder
		 */
		public Builder additionalInformation (Map<String, Object> val)
		{
			additionalInformation = val;
			return this;
		}

		/**
		 * Build cs user data.
		 *
		 * @return the cs user data
		 */
		public CSUserData build ()
		{
			return new CSUserData(this);
		}
	}
}
