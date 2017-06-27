package sk.qbsw.core.client.model.response;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;

/**
 * The simple alter collection response body - returns only the count of the affected entity.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class SimpleAlterCollectionResponseBody extends BaseResponseBody
{
	private static final long serialVersionUID = -121171424136795364L;

	@ApiModelProperty (required = true, value = "The count of the affected entity")
	@NotNull
	private Integer affectedCount;

	/**
	 * Gets affected count.
	 *
	 * @return the affected count
	 */
	public Integer getAffectedCount ()
	{
		return affectedCount;
	}

	/**
	 * Sets affected count.
	 *
	 * @param affectedCount the affected count
	 */
	public void setAffectedCount (Integer affectedCount)
	{
		this.affectedCount = affectedCount;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		SimpleAlterCollectionResponseBody that = (SimpleAlterCollectionResponseBody) o;

		return new EqualsBuilder().append(affectedCount, that.affectedCount).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).append(affectedCount).toHashCode();
	}
}
