package sk.qbsw.core.security.oauth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import sk.qbsw.core.persistence.model.domain.AEntity;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Security token held for authentication.
 *
 * @author podmajersky
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.13.0
 */
@Entity
@Table (name = "t_oauth_token ", schema = "sec")
public class CSecurityToken extends AEntity<Long> implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2280914156927086322L;

	/** The id. */
	@Id
	@SequenceGenerator (name = "S_TOKEN_PKID_GENERATOR", sequenceName = "SEC.S_OAUTH_TOKEN")
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "S_TOKEN_PKID_GENERATOR")
	@Column (name = "pk_id")
	private Long id;

	/** The token. */
	@Column (name = "c_token", unique = true, nullable = false)
	private String token;

	/** The create date. */
	@Type (type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column (name = "c_create_date", nullable = false)
	private DateTime createDate;

	/** The last access date. */
	@Type (type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column (name = "c_last_access_date", nullable = false)
	private DateTime lastAccessDate;

	/** The ip. */
	@Column (name = "c_ip", nullable = true)
	private String ip;

	/** The user. */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_user", nullable = false)
	private CUser user;

	/**
	 * On create.
	 */
	@PrePersist
	protected void onCreate ()
	{
		lastAccessDate = new DateTime();
	}

	/**
	 * On update.
	 */
	@PreUpdate
	protected void onUpdate ()
	{
		lastAccessDate = new DateTime();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.model.domain.IEntity#getId()
	 */
	@Override
	public Long getId ()
	{
		return id;
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
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken ()
	{
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken (String token)
	{
		this.token = token;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public DateTime getCreateDate ()
	{
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the new creates the date
	 */
	public void setCreateDate (DateTime createDate)
	{
		this.createDate = createDate;
	}

	/**
	 * Gets the last access date.
	 *
	 * @return the last access date
	 */
	public DateTime getLastAccessDate ()
	{
		return lastAccessDate;
	}

	/**
	 * Sets the last access date.
	 *
	 * @param lastAccessDate the new last access date
	 */
	public void setLastAccessDate (DateTime lastAccessDate)
	{
		this.lastAccessDate = lastAccessDate;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public CUser getUser ()
	{
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser (CUser user)
	{
		this.user = user;
	}

	/**
	 * Update last access.
	 */
	public void updateLastAccess ()
	{
		lastAccessDate = new DateTime();
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
}
