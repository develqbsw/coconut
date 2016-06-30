package sk.qbsw.core.security.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.google.gson.annotations.Expose;

/**
 * The block list of IP addresses where invalid authentication's data came from.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.12.2
 * @since 1.12.2
 */
@Entity
@Table (name = "t_blocked_login", schema = "sec", uniqueConstraints = @UniqueConstraint (columnNames = {"c_login", "c_ip"}))
public class CBlockedLogin extends ASecurityChangeEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1021948002972398081L;

	/** The id. */
	@Id
	@SequenceGenerator (name = "t_blocked_login_pkid_generator", sequenceName = "sec.t_blocked_login_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_blocked_login_pkid_generator")
	@Column (name = "pk_id")
	@Expose
	private Long id;

	/** The login. */
	@Column (name = "c_login", nullable = false)
	@Expose
	private String login;

	/** The ip. */
	@Column (name = "c_ip")
	@Expose
	private String ip;

	/** The blocked from. */
	@Column (name = "c_blocked_from")
	@Type (type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Expose
	private DateTime blockedFrom;

	/** The blocked to. */
	@Column (name = "c_blocked_to")
	@Type (type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Expose
	private DateTime blockedTo;

	/** The invalid login count. */
	@Column (name = "c_invalid_login_count", nullable = false)
	@Expose
	private int invalidLoginCount = 0;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.model.domain.IEntity#getId()
	 */
	@Override
	public Long getId ()
	{
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId (Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin ()
	{
		return login;
	}

	/**
	 * Sets the login.
	 *
	 * @param login the new login
	 */
	public void setLogin (String login)
	{
		this.login = login;
	}

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp ()
	{
		return ip;
	}

	/**
	 * Sets the ip.
	 *
	 * @param ip the new ip
	 */
	public void setIp (String ip)
	{
		this.ip = ip;
	}

	/**
	 * Gets the blocked from.
	 *
	 * @return the blocked from
	 */
	public DateTime getBlockedFrom ()
	{
		return blockedFrom;
	}

	/**
	 * Sets the blocked from.
	 *
	 * @param blockedFrom the new blocked from
	 */
	public void setBlockedFrom (DateTime blockedFrom)
	{
		this.blockedFrom = blockedFrom;
	}

	/**
	 * Gets the blocked to.
	 *
	 * @return the blocked to
	 */
	public DateTime getBlockedTo ()
	{
		return blockedTo;
	}

	/**
	 * Sets the blocked to.
	 *
	 * @param blockedTo the new blocked to
	 */
	public void setBlockedTo (DateTime blockedTo)
	{
		this.blockedTo = blockedTo;
	}

	/**
	 * Gets the invalid login count.
	 *
	 * @return the invalid login count
	 */
	public int getInvalidLoginCount ()
	{
		return invalidLoginCount;
	}

	/**
	 * Sets the invalid login count.
	 *
	 * @param invalidLoginCount the new invalid login count
	 */
	public void setInvalidLoginCount (int invalidLoginCount)
	{
		this.invalidLoginCount = invalidLoginCount;
	}
}
