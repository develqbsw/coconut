package sk.qbsw.organization.core.model.domain;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.persistence.model.domain.AEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * The User.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Entity
@Table (name = "t_user", schema = DatabaseSchemas.ORGANIZATION)
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue ("user")
@DiscriminatorColumn (name = "d_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public class User extends AEntity<Long>
{
	private static final long serialVersionUID = 2439789478108909587L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
	@SequenceGenerator (name = "userSequenceGenerator", sequenceName = DatabaseSchemas.ORGANIZATION + ".s_user")
	@Column (name = "pk_id")
	private Long id;

	@NotNull
	@Column (name = "c_state")
	@Enumerated (EnumType.STRING)
	private ActivityStates state = ActivityStates.ACTIVE;

	@ManyToMany (mappedBy = "users", fetch = FetchType.LAZY)
	private Set<Unit> units;
}
