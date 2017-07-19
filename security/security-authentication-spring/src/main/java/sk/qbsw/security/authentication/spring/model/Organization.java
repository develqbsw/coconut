/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.authentication.spring.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * The simplified organization.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class Organization implements Serializable
{
	private static final long serialVersionUID = 3528029332180443692L;

	@NotNull
	private Long id;

	private String name;

	private String code;

	public Organization ()
	{
	}

	private Organization (Builder builder)
	{
		setId(builder.id);
		setName(builder.name);
		setCode(builder.code);
	}

	public static Builder newBuilder ()
	{
		return new Builder();
	}

	public Long getId ()
	{
		return id;
	}

	public void setId (Long id)
	{
		this.id = id;
	}

	public String getName ()
	{
		return name;
	}

	public void setName (String name)
	{
		this.name = name;
	}

	public String getCode ()
	{
		return code;
	}

	public void setCode (String code)
	{
		this.code = code;
	}

	public static final class Builder
	{
		private Long id;
		private String name;
		private String code;

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

		public Builder code (String val)
		{
			code = val;
			return this;
		}

		public Organization build ()
		{
			return new Organization(this);
		}
	}
}
