package sk.qbsw.core.client.model.response.sync;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sk.qbsw.core.client.model.response.BaseResponseBody;

import javax.validation.constraints.NotNull;

/**
 * The synchronization response body.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public abstract class SyncDataResponseBody extends BaseResponseBody
{
	private static final long serialVersionUID = 3962724756112763241L;

	@ApiModelProperty (required = true, value = "The last update of the entity in iso format")
	@NotNull
	private String lastUpdated;

	/**
	 * Gets last updated.
	 *
	 * @return the last updated
	 */
	public String getLastUpdated ()
	{
		return lastUpdated;
	}

	/**
	 * Sets last updated.
	 *
	 * @param lastUpdated the last updated
	 */
	public void setLastUpdated (String lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		SyncDataResponseBody that = (SyncDataResponseBody) o;

		return new EqualsBuilder().append(lastUpdated, that.lastUpdated).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).append(lastUpdated).toHashCode();
	}
}
