package sk.qbsw.security.oauth.db.model.domain;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.model.domain.AuthenticationTokenBase;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The authentication token.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.1
 */
@Entity
@DiscriminatorValue ("authentication_token")
public class AuthenticationToken extends AuthenticationTokenBase<Account>
{
	private static final long serialVersionUID = -5892107129431544313L;
}
