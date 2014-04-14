package sk.qbsw.core.security.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * Abstract entity which add changeDateTime value which is date and time of last change
 * changeDateTime is automatically set when is entity updated 
 * @author Michal Lacko
 * @version 1.8.0
 * @since 1.8.0
 */

@MappedSuperclass
public abstract class ASecurityChangeEntity<T> implements Serializable, IEntity<T>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * date and time of last entity change
	 */
	@Column (name = "c_change_date_time", nullable = false)
	@Type (type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime changeDateTime;

	public ASecurityChangeEntity ()
	{
		super();
	}

	@PrePersist
	protected void onCreate ()
	{
		this.changeDateTime = new DateTime();
	}

	@PreUpdate
	protected void onUpdate ()
	{
		this.changeDateTime = new DateTime();
	}

	public DateTime getChangeDateTime ()
	{
		return this.changeDateTime;
	}

	protected void setChangeDateTime (DateTime changeDateTime)
	{
		this.changeDateTime = changeDateTime;
	}

}
