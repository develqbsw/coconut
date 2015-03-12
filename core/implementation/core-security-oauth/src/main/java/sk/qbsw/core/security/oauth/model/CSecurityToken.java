package sk.qbsw.core.security.oauth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Security token held for authentication
 * 
 * @author podmajersky
 * @version 1.13.0
 * @since 1.13.0
 *
 */
@Entity
@Table (name = "t_oauth_token ", schema = "sec")
public class CSecurityToken implements Serializable, IEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2280914156927086322L;

	@Id
	@SequenceGenerator (name = "S_TOKEN_PKID_GENERATOR", sequenceName = "SEC.S_OAUTH_TOKEN")
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "S_TOKEN_PKID_GENERATOR")
	@Column (name = "pk_id")
	private Long id;

	@Column (name = "c_token", unique = true, nullable = false)
	private String token;

	@Type (type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column (name = "c_create_date", nullable = false)
	private DateTime createDate;

	@Type (type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column (name = "c_last_access_date", nullable = false)
	private DateTime lastAccessDate;

	@Column (name = "c_ip", nullable = true)
	private String ip;

	@OneToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_user", unique = true, nullable = false)
	private CUser user;

	@PrePersist
	protected void onCreate () {
		lastAccessDate = new DateTime();
	}

	@PreUpdate
	protected void onUpdate () {
		lastAccessDate = new DateTime();
	}

	@Override
	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public String getToken () {
		return token;
	}

	public void setToken (String token) {
		this.token = token;
	}

	public DateTime getCreateDate () {
		return createDate;
	}

	public void setCreateDate (DateTime createDate) {
		this.createDate = createDate;
	}

	public DateTime getLastAccessDate () {
		return lastAccessDate;
	}

	public void setLastAccessDate (DateTime lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	public CUser getUser () {
		return user;
	}

	public void setUser (CUser user) {
		this.user = user;
	}

	public void updateLastAccess () {
		lastAccessDate = new DateTime();
	}

	public String getIp () {
		return ip;
	}

	public void setIp (String ip) {
		this.ip = ip;
	}
}
