package sk.qbsw.security.oauth.db.model.domain;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.model.domain.MasterTokenBase;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The master token.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.1
 */
@Entity
@DiscriminatorValue ("master_token")
public class MasterToken extends MasterTokenBase<Account>
{
	private static final long serialVersionUID = -375148075346298238L;
}
