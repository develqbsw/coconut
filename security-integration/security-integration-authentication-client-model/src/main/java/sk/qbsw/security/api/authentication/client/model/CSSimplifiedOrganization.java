package sk.qbsw.security.api.authentication.client.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The simplified organization.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class CSSimplifiedOrganization implements Serializable
{
	private static final long serialVersionUID = -9061103501380889630L;

	@ApiModelProperty (required = true, value = "The identifier")
	@NotNull
	private Long id;

	@ApiModelProperty (value = "The organization name")
	private String name;

	@ApiModelProperty (value = "The organization code")
	private String code;

	/**
	 * Instantiates a new Cs simplified organization.
	 */
	public CSSimplifiedOrganization ()
	{
	}

	private CSSimplifiedOrganization (Builder builder)
	{
		setId(builder.id);
		setName(builder.name);
		setCode(builder.code);
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
	 * Gets code.
	 *
	 * @return the code
	 */
	public String getCode ()
	{
		return code;
	}

	/**
	 * Sets code.
	 *
	 * @param code the code
	 */
	public void setCode (String code)
	{
		this.code = code;
	}


	/**
	 * The type Builder.
	 */
	public static final class Builder
	{
		private Long id;
		private String name;
		private String code;

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
		 * Code builder.
		 *
		 * @param val the val
		 * @return the builder
		 */
		public Builder code (String val)
		{
			code = val;
			return this;
		}

		/**
		 * Build cs simplified organization.
		 *
		 * @return the cs simplified organization
		 */
		public CSSimplifiedOrganization build ()
		{
			return new CSSimplifiedOrganization(this);
		}
	}
}
