package sk.qbsw.core.security.oauth.model.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import sk.qbsw.core.persistence.model.domain.AEntity;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Security token held for authentication.
 *
 * @author podmajersky
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.0
 */
@Entity
@Table (name = "t_oauth_token", schema = "sec")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue ("security_token")
@DiscriminatorColumn (name = "d_type", discriminatorType = DiscriminatorType.STRING)
public abstract class CSecurityToken extends AEntity<Long> implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2280914156927086322L;

	/** The id. */
	@Id
	@SequenceGenerator (name = "s_token_pkid_generator", sequenceName = "sec.s_oauth_token")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "s_token_pkid_generator")
	@Column (name = "pk_id")
	private Long id;

	/** The create date. */
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	@Column (name = "c_create_date", nullable = false)
	private OffsetDateTime createDate;

	/** The last access date. */
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	@Column (name = "c_last_access_date", nullable = false)
	private OffsetDateTime lastAccessDate;

	/** The token. */
	@Column (name = "c_token", unique = true, nullable = false)
	private String token;

	/** The ip. */
	@Column (name = "c_ip")
	private String ip;

	/** The device id. */
	@Column (name = "c_device_id", nullable = false)
	private String deviceId;

	/** The user. */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_user", nullable = false)
	private CUser user;

	/**
	 * On create - set create date and last access date.
	 */
	@PrePersist
	protected void onCreate ()
	{
		createDate = OffsetDateTime.now();
		lastAccessDate = OffsetDateTime.now();
	}

	/**
	 * On update - set last access date.
	 */
	@PreUpdate
	protected void onUpdate ()
	{
		lastAccessDate = OffsetDateTime.now();
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
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public OffsetDateTime getCreateDate ()
	{
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the new creates the date
	 */
	public void setCreateDate (OffsetDateTime createDate)
	{
		this.createDate = createDate;
	}

	/**
	 * Gets the last access date.
	 *
	 * @return the last access date
	 */
	public OffsetDateTime getLastAccessDate ()
	{
		return lastAccessDate;
	}

	/**
	 * Sets the last access date.
	 *
	 * @param lastAccessDate the new last access date
	 */
	public void setLastAccessDate (OffsetDateTime lastAccessDate)
	{
		this.lastAccessDate = lastAccessDate;
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
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public String getDeviceId ()
	{
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId the new device id
	 */
	public void setDeviceId (String deviceId)
	{
		this.deviceId = deviceId;
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
		lastAccessDate = OffsetDateTime.now();
	}
}
