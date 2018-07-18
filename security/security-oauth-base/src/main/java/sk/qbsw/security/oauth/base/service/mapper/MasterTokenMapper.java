package sk.qbsw.security.oauth.base.service.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.model.domain.MasterTokenBase;
import sk.qbsw.security.oauth.model.MasterTokenDataBase;

/**
 * The master token authenticationTokenMapper.
 *
 * @param <A> the account type
 * @param <M> the master token type
 * @param <D> the account data type
 * @param <MD> the master token data type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface MasterTokenMapper<A extends Account, M extends MasterTokenBase<A>, D extends AccountData, MD extends MasterTokenDataBase<D>>
{
	/**
	 * Map to master token data md.
	 *
	 * @param masterToken the master token
	 * @return the md
	 */
	MD mapToMasterTokenData (M masterToken);
}
