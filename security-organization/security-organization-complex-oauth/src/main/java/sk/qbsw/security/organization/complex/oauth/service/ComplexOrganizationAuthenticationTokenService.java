package sk.qbsw.security.organization.complex.oauth.service;

import sk.qbsw.security.oauth.model.AuthenticationTokenDataBase;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.organization.complex.oauth.model.ComplexOrganizationAccountData;

/**
 * The complex organization authentication token service.
 *
 * @param <D> the complex organization account data type
 * @param <T> the token type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface ComplexOrganizationAuthenticationTokenService<D extends ComplexOrganizationAccountData, T extends AuthenticationTokenDataBase<D>>extends AuthenticationTokenService<D, T>
{
}
