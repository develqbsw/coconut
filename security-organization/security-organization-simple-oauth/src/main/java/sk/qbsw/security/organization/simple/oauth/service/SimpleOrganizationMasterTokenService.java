package sk.qbsw.security.organization.simple.oauth.service;

import sk.qbsw.security.oauth.model.MasterTokenDataBase;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.organization.simple.oauth.model.SimpleOrganizationAccountData;

/**
 * The simple organization master token service.
 *
 * @param <D> the simple organization account data type
 * @param <MD> the master token data type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface SimpleOrganizationMasterTokenService<D extends SimpleOrganizationAccountData, MD extends MasterTokenDataBase<D>>extends MasterTokenService<D, MD>
{
}
