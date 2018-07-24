package sk.qbsw.security.organization.complex.oauth.service;

import sk.qbsw.security.oauth.model.MasterTokenDataBase;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.organization.complex.oauth.model.ComplexOrganizationAccountData;

/**
 * The complex organization master token service.
 *
 * @param <D> the complex organization account data type
 * @param <MD> the master token data type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface ComplexOrganizationMasterTokenService<D extends ComplexOrganizationAccountData, MD extends MasterTokenDataBase<D>>extends MasterTokenService<D, MD>
{
}
