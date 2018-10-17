package sk.qbsw.security.organization.complex.core.model.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.persistence.model.domain.AEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * The complex organization unit.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Entity (name = "complexOrganizationUnit")
@Table (name = "t_unit", schema = DatabaseSchemas.ORGANIZATION, //
	uniqueConstraints = @UniqueConstraint (name = "uc_unit_code", columnNames = {"c_code"}))
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue ("complexOrganizationUnit")
@DiscriminatorColumn (name = "d_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@EqualsAndHashCode (of = {"code", "name"}, callSuper = false)
public class CXOUnit extends AEntity<Long>
{
	private static final long serialVersionUID = 1340690291040192424L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "complexUnitSequenceGenerator")
	@SequenceGenerator (name = "complexUnitSequenceGenerator", sequenceName = DatabaseSchemas.ORGANIZATION + ".s_unit")
	@Column (name = "pk_id")
	private Long id;

	@NotNull
	@Column (name = "c_code")
	private String code;

	@NotNull
	@Column (name = "c_name")
	private String name;

	@NotNull
	@Column (name = "c_state")
	@Enumerated (EnumType.STRING)
	private ActivityStates state = ActivityStates.ACTIVE;

	@NotNull
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_organization")
	private CXOOrganization organization;

	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable (schema = DatabaseSchemas.ORGANIZATION, name = "t_x_unit_user", joinColumns = {@JoinColumn (name = "fk_unit")}, inverseJoinColumns = {@JoinColumn (name = "fk_user")})
	private Set<CXOUser> users;
}
