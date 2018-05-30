package sk.qbsw.security.core.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.persistence.model.domain.AEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

/**
 * The block list of IP addresses where invalid authentication's data came from.
 * 
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.12.2
 */
@Entity
@Table (name = "t_blocked_login", schema = DatabaseSchemas.SECURITY, //
	uniqueConstraints = @UniqueConstraint (name = "uc_blocked_login_login_ip", columnNames = {"c_login", "c_ip"}))
@Getter
@Setter
public class BlockedLogin extends AEntity<Long>
{
	private static final long serialVersionUID = -8439529532383576544L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "blockedLoginSequenceGenerator")
	@SequenceGenerator (name = "blockedLoginSequenceGenerator", sequenceName = DatabaseSchemas.SECURITY + ".s_blocked_login")
	@Column (name = "pk_id")
	private Long id;

	@NotNull
	@Column (name = "c_login")
	private String login;

	@Column (name = "c_ip")
	private String ip;

	@Column (name = "c_blocked_from")
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	private OffsetDateTime blockedFrom;

	@Column (name = "c_blocked_to")
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	private OffsetDateTime blockedTo;

	@NotNull
	@Column (name = "c_invalid_login_count")
	private int invalidLoginCount = 0;
}
