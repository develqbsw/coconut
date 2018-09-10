package sk.qbsw.security.organization.complex.oauth.service.facade;

import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountData;

/**
 * The complex organization oauth service facade.
 *
 * @param <D> the complex organization account data type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface ComplexOrganizationOAuthServiceFacade<D extends ComplexOrganizationAccountData>extends OAuthServiceFacade<D>
{
}
