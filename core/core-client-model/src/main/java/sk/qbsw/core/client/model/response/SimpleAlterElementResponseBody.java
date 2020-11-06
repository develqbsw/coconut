package sk.qbsw.core.client.model.response;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The simple alter element response - returns only the id of the affected entity.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.0
 */
public class SimpleAlterElementResponseBody extends BaseResponseBody
{
	private static final long serialVersionUID = -2163424494700866758L;

	@Schema (required = true, description = "The id of the affected entity")
	@NotNull
	private Long affectedId;

	/**
	 * Gets affected id.
	 *
	 * @return the affected id
	 */
	public Long getAffectedId ()
	{
		return affectedId;
	}

	/**
	 * Sets affected id.
	 *
	 * @param affectedId the affected id
	 */
	public void setAffectedId (Long affectedId)
	{
		this.affectedId = affectedId;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		SimpleAlterElementResponseBody that = (SimpleAlterElementResponseBody) o;

		return new EqualsBuilder().append(affectedId, that.affectedId).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).append(affectedId).toHashCode();
	}
}
