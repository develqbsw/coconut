/*
 * Developed by QBSW a.s.
 */
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
 * The authentication parameters for user.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
@Entity
@Table (name = "t_auth_params", schema = DatabaseSchemas.SECURITY)
@Getter
@Setter
public class AuthenticationParams extends AEntity<Long>
{
	private static final long serialVersionUID = -5486444008555065746L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "authParamsSequenceGenerator")
	@SequenceGenerator (name = "authParamsSequenceGenerator", sequenceName = DatabaseSchemas.SECURITY + ".s_auth_params")
	@Column (name = "pk_id")
	private Long id;

	@Column (name = "c_password")
	private String password;

	@Column (name = "c_password_digest")
	private String passwordDigest;

	@NotNull
	@Column (name = "c_password_type")
	@Enumerated (EnumType.STRING)
	private PasswordTypes passwordType = PasswordTypes.DURABLE;

	@Column (name = "c_pin")
	private String pin;

	@Column (name = "c_valid_from")
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	private OffsetDateTime validFrom;

	@Column (name = "c_valid_to")
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	private OffsetDateTime validTo;

	@NotNull
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_account")
	private Account account;

	/**
	 * Gets authentication type.
	 *
	 * @return the authentication type
	 */
	public AuthenticationTypes getAuthenticationType ()
	{
		return getPasswordDigest() != null ? AuthenticationTypes.BY_PASSWORD_DIGEST : AuthenticationTypes.BY_PASSWORD;
	}
}
