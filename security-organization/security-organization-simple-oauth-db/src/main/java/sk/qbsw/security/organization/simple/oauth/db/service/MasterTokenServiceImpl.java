package sk.qbsw.security.organization.simple.oauth.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.oauth.base.configuration.OAuthValidationConfigurator;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.base.service.IdGeneratorService;
import sk.qbsw.security.oauth.base.service.MasterTokenServiceBase;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapper;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.oauth.db.model.domain.AuthenticationToken;
import sk.qbsw.security.organization.simple.oauth.db.model.domain.MasterToken;
import sk.qbsw.security.organization.simple.oauth.model.MasterTokenData;
import sk.qbsw.security.organization.simple.oauth.service.SimpleOrganizationMasterTokenService;

import java.util.List;

/**
 * The simple organization master token service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class MasterTokenServiceImpl extends MasterTokenServiceBase<Account, AuthenticationToken, MasterToken, SimpleOrganizationAccountData, MasterTokenData> implements SimpleOrganizationMasterTokenService<SimpleOrganizationAccountData, MasterTokenData>
{
	/**
	 * Instantiates a new Master token service.
	 *
	 * @param masterTokenDao the master token dao
	 * @param authenticationTokenDao the authentication token dao
	 * @param masterTokenMapper the master token mapper
	 * @param simpleOrganizationAccountMapperImpl the simple organization account mapper
	 * @param accountDao the account dao
	 * @param idGeneratorService the id generator service
	 * @param validationConfiguration the validation configuration
	 */
	public MasterTokenServiceImpl (MasterTokenDao<Account, MasterToken> masterTokenDao, AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao, //
		MasterTokenMapper<Account, MasterToken, SimpleOrganizationAccountData, MasterTokenData> masterTokenMapper, AccountOutputDataMapper<SimpleOrganizationAccountData, Account> simpleOrganizationAccountMapperImpl, //
		AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		super(masterTokenDao, authenticationTokenDao, masterTokenMapper, simpleOrganizationAccountMapperImpl, accountDao, idGeneratorService, validationConfiguration);
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
	public SimpleOrganizationAccountData getAccountByMasterToken (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		return super.getAccountByMasterTokenBase(token, deviceId, ip, isIpIgnored);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<MasterTokenData> findExpiredMasterTokens ()
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
