package sk.qbsw.security.organization.simple.oauth.service;

import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.organization.simple.oauth.model.SimpleOrganizationAccountData;

/**
 * The simple organization oauth service.
 *
 * @param <D> the simple organization account data type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface SimpleOrganizationOAuthService<D extends SimpleOrganizationAccountData>extends OAuthService<D>
{
}
