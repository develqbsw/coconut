package sk.qbsw.security.oauth.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.base.model.GeneratedTokenData;
import sk.qbsw.security.oauth.base.service.IdGeneratorService;
import sk.qbsw.security.oauth.base.service.MasterTokenService;
import sk.qbsw.security.oauth.base.service.MasterTokenServiceBase;
import sk.qbsw.security.oauth.base.configuration.OAuthValidationConfigurator;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.MasterToken;

import java.util.List;

/**
 * The master token service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
public class MasterTokenServiceImpl extends MasterTokenServiceBase<Account, AuthenticationToken, MasterToken> implements MasterTokenService<Account, MasterToken>
{
	/**
	 * Instantiates a new Master token service.
	 *
	 * @param masterTokenDao the master token dao
	 * @param authenticationTokenDao the authentication token dao
	 * @param accountDao the account dao
	 * @param idGeneratorService the id generator service
	 * @param validationConfiguration the validation configuration
	 */
	public MasterTokenServiceImpl (MasterTokenDao<Account, MasterToken> masterTokenDao, AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		super(masterTokenDao, authenticationTokenDao, accountDao, idGeneratorService, validationConfiguration);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GeneratedTokenData generateMasterToken (Long accountId, String deviceId, String ip) throws CBusinessException
	{
		return super.generateMasterTokenBase(accountId, deviceId, ip);
	}

	@Override
	protected MasterToken createMasterToken (String deviceId, String ip, String token, Account account)
	{
		MasterToken masterToken = new MasterToken();
		masterToken.setDeviceId(deviceId);
		masterToken.setIp(ip);
		masterToken.setToken(idGeneratorService.getGeneratedId());
		masterToken.setAccount(account);

		return masterToken;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void revokeMasterToken (Long accountId, String masterToken) throws CBusinessException
	{
		super.revokeMasterTokenBase(accountId, masterToken);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account getAccountByMasterToken (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		return super.getAccountByMasterTokenBase(token, deviceId, ip, isIpIgnored);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<MasterToken> findExpiredMasterTokens ()
	{
		return super.findExpiredMasterTokensBase();
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Long removeMasterTokens (List<Long> ids)
	{
		return super.removeMasterTokensBase(ids);
	}
}
