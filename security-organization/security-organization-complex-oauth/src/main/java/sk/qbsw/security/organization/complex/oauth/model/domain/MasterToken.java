package sk.qbsw.security.organization.complex.oauth.model.domain;

import sk.qbsw.security.oauth.base.model.domain.MasterTokenBase;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The complex organization master token.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Entity
@DiscriminatorValue ("complex_organization_master_token")
public class MasterToken extends MasterTokenBase<UserAccount>
{
	private static final long serialVersionUID = -5277837134779088807L;
}
