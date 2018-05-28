package sk.qbsw.security.core.model.domain;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.persistence.model.domain.AEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * The Unit.
 * 
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
@Entity
@Table (name = "t_unit", schema = DatabaseSchemas.SECURITY, //
	uniqueConstraints = @UniqueConstraint (name = "uc_unit_name", columnNames = {"c_name"}))
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue ("unit")
@DiscriminatorColumn (name = "d_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public class Unit extends AEntity<Long>
{
	private static final long serialVersionUID = 4512230447879006057L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "unitSequenceGenerator")
	@SequenceGenerator (name = "unitSequenceGenerator", sequenceName = DatabaseSchemas.SECURITY + ".s_unit")
	@Column (name = "pk_id")
	private Long id;

	@NotNull
	@Column (name = "c_name")
	private String name;

	@NotNull
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_organization")
	private Organization organization;

	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable (schema = "sec", name = "t_x_group_unit", joinColumns = {@JoinColumn (name = "fk_unit")}, inverseJoinColumns = {@JoinColumn (name = "fk_group")})
	private Set<Group> groups;

	@OneToMany (mappedBy = "unit", fetch = FetchType.LAZY)
	private Set<AccountUnitGroup> accountUnitGroups;
}
