package sk.qbsw.security.organization.simple.oauth.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.configuration.OAuthValidationConfigurator;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.base.model.GeneratedTokenData;
import sk.qbsw.security.oauth.base.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.base.service.AuthenticationTokenServiceBase;
import sk.qbsw.security.oauth.base.service.IdGeneratorService;
import sk.qbsw.security.organization.simple.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.organization.simple.oauth.model.domain.MasterToken;

import java.util.List;

/**
 * The simple organization authentication token service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class AuthenticationTokenServiceImpl extends AuthenticationTokenServiceBase<Account, AuthenticationToken, MasterToken> implements AuthenticationTokenService<Account, AuthenticationToken>
{
	/**
	 * Instantiates a new Base token service.
	 *
	 * @param masterTokenDao the master token dao
	 * @param authenticationTokenDao the authentication token dao
	 * @param accountDao the account dao
	 * @param idGeneratorService the id generator service
	 * @param validationConfiguration the validation configuration
	 */
	public AuthenticationTokenServiceImpl (MasterTokenDao<Account, MasterToken> masterTokenDao, AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		super(masterTokenDao, authenticationTokenDao, accountDao, idGeneratorService, validationConfiguration);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GeneratedTokenData generateAuthenticationToken (Long accountId, String masterToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		return super.generateAuthenticationTokenBase(accountId, masterToken, deviceId, ip, isIpIgnored);
	}

	@Override
	protected AuthenticationToken createAuthenticationToken (String deviceId, String ip, String token, Account account)
	{
		AuthenticationToken authenticationToken = new AuthenticationToken();
		authenticationToken.setDeviceId(deviceId);
		authenticationToken.setIp(ip);
		authenticationToken.setToken(idGeneratorService.getGeneratedId());
		authenticationToken.setAccount(account);

		return authenticationToken;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void revokeAuthenticationToken (Long accountId, String authenticationToken) throws CBusinessException
	{
		super.revokeAuthenticationTokenBase(accountId, authenticationToken);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account getAccountByAuthenticationToken (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		return getAccountByAuthenticationTokenBase(token, deviceId, ip, isIpIgnored);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AuthenticationToken> findExpiredAuthenticationTokens ()
	{
		return findExpiredAuthenticationTokensBase();
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Long removeAuthenticationTokens (List<Long> ids)
	{
		return super.removeAuthenticationTokensBase(ids);
	}
}
