package sk.qbsw.security.organization.complex.oauth.model.domain;

import sk.qbsw.security.oauth.base.model.domain.AuthenticationTokenBase;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 * The complex organization authentication token.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Entity
@DiscriminatorValue ("complex_organization_authentication_token")
public class AuthenticationToken extends AuthenticationTokenBase<UserAccount>
{
	private static final long serialVersionUID = -757877900520285268L;
}
