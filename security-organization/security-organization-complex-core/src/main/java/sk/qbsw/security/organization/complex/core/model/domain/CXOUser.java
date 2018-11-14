package sk.qbsw.security.organization.complex.core.model.domain;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.core.model.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * The complex organization user.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
@Entity
@DiscriminatorValue ("complexOrganizationUser")
@Getter
@Setter
public class CXOUser extends User
{
	private static final long serialVersionUID = -6472530726731850552L;

	@NotNull
	@Column (name = "c_state")
	@Enumerated (EnumType.STRING)
	private ActivityStates state = ActivityStates.ACTIVE;

	@ManyToMany (mappedBy = "users", fetch = FetchType.LAZY)
	private Set<CXOUnit> units;
}
