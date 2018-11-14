package sk.qbsw.security.core.model.domain;

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
 * The organization.
 *
 * @author Dalibor Rak
 * @author Michal Lacko
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.0.0
 */
@Entity (name = "organization")
@Table (name = "t_organization", schema = DatabaseSchemas.SECURITY, //
	uniqueConstraints = @UniqueConstraint (name = "uc_organization_code", columnNames = {"c_code"}))
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue ("organization")
@DiscriminatorColumn (name = "d_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public class Organization extends AEntity<Long>
{
	private static final long serialVersionUID = 5043428606865534450L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "organizationSequenceGenerator")
	@SequenceGenerator (name = "organizationSequenceGenerator", sequenceName = DatabaseSchemas.SECURITY + ".s_organization")
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
	private Set<Account> accounts = new HashSet<>();
}
