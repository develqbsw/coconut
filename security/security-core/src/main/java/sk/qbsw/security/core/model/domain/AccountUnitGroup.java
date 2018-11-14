package sk.qbsw.security.core.model.domain;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.persistence.model.domain.AEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Cross entity for account, unit and group.
 *
 * @author Roman Farkas
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.6.4
 */
@Entity
@Table (name = "t_x_account_unit_group", schema = DatabaseSchemas.SECURITY)
@Getter
@Setter
public class AccountUnitGroup extends AEntity<Long>
{
	private static final long serialVersionUID = 7083824112522940968L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "accountUnitGroupSequenceGenerator")
	@SequenceGenerator (name = "accountUnitGroupSequenceGenerator", sequenceName = DatabaseSchemas.SECURITY + ".s_x_account_unit_group")
	@Column (name = "pk_id")
	private Long id;

	@NotNull
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_account")
	private Account account;

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_unit")
	private Unit unit;

	@NotNull
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_group")
	private Group group;
}
