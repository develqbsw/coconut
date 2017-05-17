package sk.qbsw.integration.mailchimp.client.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import io.swagger.annotations.ApiModelProperty;

/**
 * The mailchimp link.
 * 
 * @author Juraj Vrabec
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public class Link extends BaseEntity
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2183540859157459803L;

	/** As with an HTML ‘rel’ attribute, this describes the type of link. */
	@ApiModelProperty (required = false, value = "As with an HTML ‘rel’ attribute, this describes the type of link.")
	private String rel;

	/** This property contains a fully-qualified URL that can be called to retrieve the linked resource or perform the linked action. */
	@ApiModelProperty (required = false, value = "This property contains a fully-qualified URL that can be called to retrieve the linked resource or perform the linked action.")
	private String href;

	/** The HTTP method that should be used when accessing the URL defined in ‘href’. Possible Values: GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD. */
	@ApiModelProperty (required = false, value = "The HTTP method that should be used when accessing the URL defined in ‘href’. Possible Values: GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD.")
	private String method;

	/** For GETs, this is a URL representing the schema that the response should conform to. */
	@ApiModelProperty (required = false, value = "For GETs, this is a URL representing the schema that the response should conform to.")
	private String targetSchema;

	/** For HTTP methods that can receive bodies (POST and PUT), this is a URL representing the schema that the body should conform to. */
	@ApiModelProperty (required = false, value = "For HTTP methods that can receive bodies (POST and PUT), this is a URL representing the schema that the body should conform to.")
	private String schema;

	/**
	 * Gets the rel.
	 *
	 * @return the rel
	 */
	public String getRel ()
	{
		return rel;
	}

	/**
	 * Sets the rel.
	 *
	 * @param rel the new rel
	 */
	public void setRel (String rel)
	{
		this.rel = rel;
	}

	/**
	 * Gets the href.
	 *
	 * @return the href
	 */
	public String getHref ()
	{
		return href;
	}

	/**
	 * Sets the href.
	 *
	 * @param href the new href
	 */
	public void setHref (String href)
	{
		this.href = href;
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public String getMethod ()
	{
		return method;
	}

	/**
	 * Sets the method.
	 *
	 * @param method the new method
	 */
	public void setMethod (String method)
	{
		this.method = method;
	}

	/**
	 * Gets the target schema.
	 *
	 * @return the target schema
	 */
	public String getTargetSchema ()
	{
		return targetSchema;
	}

	/**
	 * Sets the target schema.
	 *
	 * @param targetSchema the new target schema
	 */
	public void setTargetSchema (String targetSchema)
	{
		this.targetSchema = targetSchema;
	}

	/**
	 * Gets the schema.
	 *
	 * @return the schema
	 */
	public String getSchema ()
	{
		return schema;
	}

	/**
	 * Sets the schema.
	 *
	 * @param schema the new schema
	 */
	public void setSchema (String schema)
	{
		this.schema = schema;
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
		Link castOther = (Link) other;
		return new EqualsBuilder().append(rel, castOther.rel).append(href, castOther.href).append(method, castOther.method).append(targetSchema, castOther.targetSchema).append(schema, castOther.schema).isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(2067499757, 1591022641).append(rel).append(href).append(method).append(targetSchema).append(schema).toHashCode();
	}
}
