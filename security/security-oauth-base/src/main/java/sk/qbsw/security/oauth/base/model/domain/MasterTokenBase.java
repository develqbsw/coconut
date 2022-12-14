package sk.qbsw.security.oauth.base.model.domain;

import sk.qbsw.security.core.model.domain.Account;

import javax.persistence.Entity;

/**
 * The base master token.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Entity
public abstract class MasterTokenBase<A extends Account>extends SecurityTokenBase<A>
{
	private static final long serialVersionUID = -1789688838991577978L;
}
