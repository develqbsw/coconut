package sk.qbsw.security.organization.complex.oauth.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.service.mapper.AccountMapper;
import sk.qbsw.security.oauth.base.configuration.OAuthValidationConfigurator;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.base.service.AuthenticationTokenServiceBase;
import sk.qbsw.security.oauth.base.service.IdGeneratorService;
import sk.qbsw.security.oauth.base.service.mapper.AuthenticationTokenMapper;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;
import sk.qbsw.security.organization.complex.oauth.model.AuthenticationTokenData;
import sk.qbsw.security.organization.complex.oauth.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.organization.complex.oauth.model.domain.MasterToken;

import java.util.List;

/**
 * The complex organization authentication token service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class AuthenticationTokenServiceImpl extends AuthenticationTokenServiceBase<UserAccount, AuthenticationToken, MasterToken, ComplexOrganizationAccountData, AuthenticationTokenData> implements AuthenticationTokenService<ComplexOrganizationAccountData, AuthenticationTokenData>
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
	public AuthenticationTokenServiceImpl (MasterTokenDao<UserAccount, MasterToken> masterTokenDao, AuthenticationTokenDao<UserAccount, AuthenticationToken> authenticationTokenDao, //
		AuthenticationTokenMapper<UserAccount, AuthenticationToken, ComplexOrganizationAccountData, AuthenticationTokenData> authenticationTokenMapper, AccountMapper<ComplexOrganizationAccountData, UserAccount> accountMapper, //
		AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		super(masterTokenDao, authenticationTokenDao, authenticationTokenMapper, accountMapper, accountDao, idGeneratorService, validationConfiguration);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GeneratedTokenData generateAuthenticationToken (Long accountId, String masterToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		return super.generateAuthenticationTokenBase(accountId, masterToken, deviceId, ip, isIpIgnored);
	}

	@Override
	protected AuthenticationToken createAuthenticationToken (String deviceId, String ip, String token, UserAccount account)
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
	public ComplexOrganizationAccountData getAccountByAuthenticationToken (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		return getAccountByAuthenticationTokenBase(token, deviceId, ip, isIpIgnored);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AuthenticationTokenData> findExpiredAuthenticationTokens ()
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
