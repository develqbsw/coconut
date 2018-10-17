package sk.qbsw.security.organization.complex.core.model.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.persistence.model.domain.AEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * The complex organization.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Entity (name = "complexOrganization")
@Table (name = "t_organization", schema = DatabaseSchemas.ORGANIZATION, //
	uniqueConstraints = @UniqueConstraint (name = "uc_organization_code", columnNames = {"c_code"}))
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue ("complexOrganization")
@DiscriminatorColumn (name = "d_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@EqualsAndHashCode (of = {"code", "name"}, callSuper = false)
public class CXOOrganization extends AEntity<Long>
{
	private static final long serialVersionUID = -2623910951096078692L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "complexOrganizationSequenceGenerator")
	@SequenceGenerator (name = "complexOrganizationSequenceGenerator", sequenceName = DatabaseSchemas.ORGANIZATION + ".s_organization")
	@Column (name = "pk_id")
	private Long id;

	@NotNull
	@Column (name = "c_code")
	private String code;

	@NotNull
	@Column (name = "c_name")
	private String name;

	@Column (name = "c_email")
	private String email;

	@NotNull
	@Column (name = "c_state")
	@Enumerated (EnumType.STRING)
	private ActivityStates state = ActivityStates.ACTIVE;

	@OneToMany (mappedBy = "organization", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<CXOUnit> units = new HashSet<>();
}
