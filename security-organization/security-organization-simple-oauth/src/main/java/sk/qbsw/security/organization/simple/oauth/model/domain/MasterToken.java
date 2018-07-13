package sk.qbsw.security.organization.simple.oauth.model.domain;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.model.domain.MasterTokenBase;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The simple organization master token.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Entity
@DiscriminatorValue ("simple_organization_master_token")
public class MasterToken extends MasterTokenBase<Account>
{
	private static final long serialVersionUID = -4366682268689216851L;
}
