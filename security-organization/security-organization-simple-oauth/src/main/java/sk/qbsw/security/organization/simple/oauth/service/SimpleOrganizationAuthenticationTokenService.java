package sk.qbsw.security.organization.simple.oauth.service;

import sk.qbsw.security.oauth.model.AuthenticationTokenDataBase;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountData;

/**
 * The simple organization authentication token service.
 *
 * @param <D> the simple organization account data type
 * @param <T> the token type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface SimpleOrganizationAuthenticationTokenService<D extends SimpleOrganizationAccountData, T extends AuthenticationTokenDataBase<D>>extends AuthenticationTokenService<D, T>
{
}
