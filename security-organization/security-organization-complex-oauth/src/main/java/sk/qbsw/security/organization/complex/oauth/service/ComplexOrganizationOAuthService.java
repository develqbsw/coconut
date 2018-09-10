package sk.qbsw.security.organization.complex.oauth.service;

import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountData;

/**
 * The complex organization oauth service.
 *
 * @param <D> the complex organization account data type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface ComplexOrganizationOAuthService<D extends ComplexOrganizationAccountData>extends OAuthService<D>
{
}
