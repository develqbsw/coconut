package sk.qbsw.core.client.model.response.sync;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The synchronization response body with one item.
 *
 * @param <E> the element type
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.0
 */
public abstract class SyncElementResponseBody<E>extends SyncDataResponseBody
{
	private static final long serialVersionUID = 443986927834518981L;

	@Schema (description = "The updated entity")
	private E updated;

	/**
	 * Gets updated.
	 *
	 * @return the updated
	 */
	public E getUpdated ()
	{
		return updated;
	}

	/**
	 * Sets updated.
	 *
	 * @param updated the updated
	 */
	public void setUpdated (E updated)
	{
		this.updated = updated;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		SyncElementResponseBody<?> that = (SyncElementResponseBody<?>) o;

		return new EqualsBuilder().appendSuper(super.equals(o)).append(updated, that.updated).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(updated).toHashCode();
	}
}
