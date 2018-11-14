package sk.qbsw.security.core.model.domain;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.persistence.model.domain.AEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * The Role.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Entity
@Table (name = "t_role", schema = DatabaseSchemas.SECURITY, //
	uniqueConstraints = @UniqueConstraint (name = "uc_role_code", columnNames = {"c_code"}))
@Getter
@Setter
public class Role extends AEntity<Long>
{
	private static final long serialVersionUID = 9197884851803513494L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "roleSequenceGenerator")
	@SequenceGenerator (name = "roleSequenceGenerator", sequenceName = DatabaseSchemas.SECURITY + ".s_role")
	@Column (name = "pk_id")
	private Long id;

	@NotNull
	@Column (name = "c_code")
	private String code;

	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable (schema = "sec", name = "t_x_group_role", joinColumns = {@JoinColumn (name = "fk_role")}, inverseJoinColumns = {@JoinColumn (name = "fk_group")})
	private Set<Group> groups;
}
