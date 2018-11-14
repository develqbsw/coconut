package sk.qbsw.security.core.model.domain;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.persistence.model.domain.AEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * The User.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Entity
@Table (name = "t_user", schema = DatabaseSchemas.SECURITY)
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
	@SequenceGenerator (name = "userSequenceGenerator", sequenceName = DatabaseSchemas.SECURITY + ".s_user")
	@Column (name = "pk_id")
	private Long id;

	@OneToMany (mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Account> accounts;
}
