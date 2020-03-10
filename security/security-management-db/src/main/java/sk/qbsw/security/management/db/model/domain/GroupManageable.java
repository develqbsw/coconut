package sk.qbsw.security.management.db.model.domain;

import org.hibernate.annotations.Type;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.User;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

/**
 * The group manageable.
 *
 * @author Michal Slezák
 * @version 2.5.0
 * @since 2.5.0
 */
@Entity
@DiscriminatorValue ("groupManageable")
public class GroupManageable extends Group implements Manageable
{
	private static final long serialVersionUID = -7094025853718666036L;

	@NotNull
	@Column (name = "c_updated")
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	private OffsetDateTime updated;

	@NotNull
	@OneToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_updated_by")
	private User updatedBy;

	@Override
	public OffsetDateTime getUpdated ()
	{
		return this.updated;
	}

	@Override
	public void setUpdated (OffsetDateTime offsetDateTime)
	{
		this.updated = offsetDateTime;
	}

	@Override
	public User getUpdatedBy ()
	{
		return this.updatedBy;
	}

	@Override
	public void setUpdatedBy (User user)
	{
		this.updatedBy = user;
	}
}
