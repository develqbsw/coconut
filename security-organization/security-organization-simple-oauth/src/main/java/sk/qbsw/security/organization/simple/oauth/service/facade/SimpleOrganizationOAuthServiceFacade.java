package sk.qbsw.security.organization.simple.oauth.service.facade;

import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountData;

/**
 * The simple organization oauth service facade.
 *
 * @param <D> the simple organization account data type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface SimpleOrganizationOAuthServiceFacade<D extends SimpleOrganizationAccountData>extends OAuthServiceFacade<D>
{
}
