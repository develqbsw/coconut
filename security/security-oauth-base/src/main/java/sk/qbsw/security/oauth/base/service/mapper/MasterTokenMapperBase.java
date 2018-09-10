package sk.qbsw.security.oauth.base.service.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.oauth.base.model.domain.MasterTokenBase;
import sk.qbsw.security.oauth.model.MasterTokenDataBase;

/**
 * The master token mapper base.
 *
 * @param <A> the account type
 * @param <M> the master token type
 * @param <D> the account data type
 * @param <MD> the master token data type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public abstract class MasterTokenMapperBase<A extends Account, M extends MasterTokenBase<A>, D extends AccountData, MD extends MasterTokenDataBase<D>> implements MasterTokenMapper<A, M, D, MD>
{
	/**
	 * The Account mapper.
	 */
	protected final AccountOutputDataMapper<D, A> accountOutputDataMapper;

	/**
	 * Instantiates a new Authentication token mapper base.
	 *
	 * @param accountOutputDataMapper the account output data mapper
	 */
	protected MasterTokenMapperBase (AccountOutputDataMapper<D, A> accountOutputDataMapper)
	{
		this.accountOutputDataMapper = accountOutputDataMapper;
	}

	@Override
	public MD mapToMasterTokenData (M masterToken)
	{
		MD masterTokenData = instantiateWithCustomAttributes(masterToken);

		masterTokenData.setId(masterToken.getId());
		masterTokenData.setCreated(masterToken.getCreateDate());
		masterTokenData.setLastAccessed(masterToken.getLastAccessDate());
		masterTokenData.setToken(masterToken.getToken());
		masterTokenData.setDeviceId(masterToken.getDeviceId());
		masterTokenData.setIp(masterToken.getIp());
		masterTokenData.setAccountData(accountOutputDataMapper.mapToAccountOutputData(masterToken.getAccount()));

		return masterTokenData;
	}

	/**
	 * Instantiate with custom attributes td.
	 *
	 * @param masterToken the master token
	 * @return the td
	 */
	protected abstract MD instantiateWithCustomAttributes (M masterToken);
}
