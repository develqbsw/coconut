package sk.qbsw.security.organization.simple.oauth.db.model.domain;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.model.domain.AuthenticationTokenBase;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 * The simple organization authentication token.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Entity
@DiscriminatorValue ("simple_organization_authentication_token")
public class AuthenticationToken extends AuthenticationTokenBase<Account>
{
	private static final long serialVersionUID = -8363937782500618600L;
}
