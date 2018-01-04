package sk.qbsw.core.client.model.response.sync;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * The synchronization response body with list of deleted items.
 *
 * @param <E> the type parameter
 * @param <K> the type parameter
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public abstract class SyncCollectionResponseBody<E, K> extends SyncDataResponseBody
{
    private static final long serialVersionUID = 443986927834518981L;

    @ApiModelProperty(required = true, value = "The updated entities")
    @NotNull
    private List<E> updated = new ArrayList<>();

    @ApiModelProperty(required = true, value = "The list of deleted items")
    @NotNull
    private List<K> deleted = new ArrayList<>();

    /**
     * Gets updated.
     *
     * @return the updated
     */
    public List<E> getUpdated()
    {
        return updated;
    }

    /**
     * Sets updated.
     *
     * @param updated the updated
     */
    public void setUpdated(List<E> updated)
    {
        this.updated = updated;
    }

    /**
     * Gets deleted.
     *
     * @return the deleted
     */
    public List<K> getDeleted()
    {
        return deleted;
    }

    /**
     * Sets deleted.
     *
     * @param deleted the deleted
     */
    public void setDeleted(List<K> deleted)
    {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SyncCollectionResponseBody<?, ?> that = (SyncCollectionResponseBody<?, ?>) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(updated, that.updated).append(deleted, that.deleted).isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(updated).append(deleted).toHashCode();
    }
}
