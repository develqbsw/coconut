package sk.qbsw.security.oauth.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.persistence.model.domain.AEntity;
import sk.qbsw.security.core.model.domain.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

/**
 * Security token held for authentication.
 *
 * @author podmajersky
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.0
 */
@Entity
@Table (name = "t_oauth_token", schema = DatabaseSchemas.SECURITY, //
	uniqueConstraints = @UniqueConstraint (name = "uc_security_token_token", columnNames = {"c_token"}))
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue ("security_token")
@DiscriminatorColumn (name = "d_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class SecurityToken extends AEntity<Long>
{
	private static final long serialVersionUID = -2280914156927086322L;

	@Id
	@NotNull
	@SequenceGenerator (name = "tokenSequenceGenerator", sequenceName = DatabaseSchemas.SECURITY + ".s_oauth_token")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "tokenSequenceGenerator")
	@Column (name = "pk_id")
	private Long id;

	@NotNull
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	@Column (name = "c_create_date")
	private OffsetDateTime createDate;

	@NotNull
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	@Column (name = "c_last_access_date")
	private OffsetDateTime lastAccessDate;

	@NotNull
	@Column (name = "c_token")
	private String token;

	@Column (name = "c_ip")
	private String ip;

	@NotNull
	@Column (name = "c_device_id")
	private String deviceId;

	@NotNull
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_account")
	private Account account;

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

	/**
	 * Update last access.
	 */
	public void updateLastAccess ()
	{
		lastAccessDate = OffsetDateTime.now();
	}
}
