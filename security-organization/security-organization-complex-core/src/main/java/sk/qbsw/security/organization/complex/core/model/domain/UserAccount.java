package sk.qbsw.security.organization.complex.core.model.domain;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.organization.core.model.domain.User;
import sk.qbsw.security.core.model.domain.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * The type User account.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Entity
@DiscriminatorValue ("userAccount")
@Getter
@Setter
public class UserAccount extends Account
{
	private static final long serialVersionUID = 4794735846789193928L;

	@NotNull
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_user")
	private User user;
}
