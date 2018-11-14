package sk.qbsw.security.oauth.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.security.base.exception.AccessDeniedException;
import sk.qbsw.core.security.base.exception.AuthenticationException;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.oauth.model.*;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The oauth service base.
 *
 * @param <A> the account type
 * @param <D> the account data type
 * @param <T> the authentication token type
 * @param <M> the master token type
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class OAuthServiceBase<A extends Account, D extends AccountData, T extends AuthenticationTokenDataBase<D>, M extends MasterTokenDataBase<D>>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(OAuthServiceBase.class);

	/**
	 * The Master token service.
	 */
	protected final MasterTokenService<D, M> masterTokenService;

	/**
	 * The Authentication token service.
	 */
	protected final AuthenticationTokenService<D, T> authenticationTokenService;

	/**
	 * The Authentication service.
	 */
	protected final AuthenticationService authenticationService;

	/**
	 * The Account output data mapper.
	 */
	protected final AccountOutputDataMapper<D, A> accountOutputDataMapper;

	/**
	 * Instantiates a new O auth service.
	 *
	 * @param masterTokenService the master token service
	 * @param authenticationTokenService the authentication token service
	 * @param authenticationService the authentication service
	 * @param accountOutputDataMapper the account output data mapper
	 */
	protected OAuthServiceBase (MasterTokenService<D, M> masterTokenService, AuthenticationTokenService<D, T> authenticationTokenService, AuthenticationService authenticationService, AccountOutputDataMapper<D, A> accountOutputDataMapper)
	{
		this.masterTokenService = masterTokenService;
		this.authenticationTokenService = authenticationTokenService;
		this.authenticationService = authenticationService;
		this.accountOutputDataMapper = accountOutputDataMapper;
	}

	/**
	 * Authenticate base authentication data.
	 *
	 * @param login the login
	 * @param password the password
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @return the authentication data
	 * @throws CBusinessException the c business exception
	 */
	protected AuthenticationData<D> authenticateBase (String login, String password, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		Account account = authenticationService.login(login, password);

		if (account == null)
		{
			throw new CSecurityException(ECoreErrorResponse.ACCOUNT_NOT_FOUND);
		}

		// generate tokens
		GeneratedTokenData masterTokenData = masterTokenService.generateMasterToken(account.getId(), deviceId, ip);
		GeneratedTokenData authenticationTokenData = authenticationTokenService.generateAuthenticationToken(account.getId(), masterTokenData.getGeneratedToken(), deviceId, ip, isIpIgnored);

		// create response
		return new AuthenticationData<>(masterTokenData, authenticationTokenData, createAccountData((A) account, createAdditionalInformation(account.getId())));
	}

	/**
	 * Reauthenticate base generated token data.
	 *
	 * @param masterToken the master token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @return the generated token data
	 * @throws CBusinessException the c business exception
	 */
	protected GeneratedTokenData reauthenticateBase (String masterToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		AccountData account = masterTokenService.getAccountByMasterToken(masterToken, deviceId, ip, isIpIgnored);

		if (account == null)
		{
			throw new CSecurityException(ECoreErrorResponse.ACCOUNT_NOT_FOUND);
		}

		return authenticationTokenService.generateAuthenticationToken(account.getId(), masterToken, deviceId, ip, isIpIgnored);
	}

	/**
	 * Invalidate base.
	 *
	 * @param masterToken the master token
	 * @param authenticationToken the authentication token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @throws CBusinessException the c business exception
	 */
	protected void invalidateBase (String masterToken, String authenticationToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		AccountData account = masterTokenService.getAccountByMasterToken(masterToken, deviceId, ip, isIpIgnored);

		if (account == null)
		{
			throw new CSecurityException(ECoreErrorResponse.ACCOUNT_NOT_FOUND);
		}

		masterTokenService.revokeMasterToken(account.getId(), masterToken);
		authenticationTokenService.revokeAuthenticationToken(account.getId(), authenticationToken);
	}

	/**
	 * Verify base verification data.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @return the verification data
	 * @throws CBusinessException the c business exception
	 */
	protected VerificationData<D> verifyBase (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		try
		{
			D accountByMasterToken = masterTokenService.getAccountByMasterToken(token, deviceId, ip, isIpIgnored);
			D accountByAuthenticationToken = authenticationTokenService.getAccountByAuthenticationToken(token, deviceId, ip, isIpIgnored);

			if (accountByMasterToken != null)
			{
				accountByMasterToken.setAdditionalInformation(createAdditionalInformation(accountByMasterToken.getId()));
				return new VerificationData<>(accountByMasterToken, VerificationTypes.MASTER_TOKEN_VERIFICATION);
			}
			else if (accountByAuthenticationToken != null)
			{
				accountByAuthenticationToken.setAdditionalInformation(createAdditionalInformation(accountByAuthenticationToken.getId()));
				return new VerificationData<>(accountByAuthenticationToken, VerificationTypes.AUTHENTICATION_TOKEN_VERIFICATION);
			}
			else
			{
				throw new AccessDeniedException("The exception in token verification process", ECoreErrorResponse.ACCESS_DENIED);
			}
		}
		catch (Exception e)
		{
			LOGGER.error("The exception in token verification process");
			throw new AuthenticationException("The exception in token verification process", e, ECoreErrorResponse.ACCESS_DENIED);
		}
	}

	/**
	 * Remove expired tokens base list.
	 *
	 * @return the list
	 */
	protected List<ExpiredTokenData> removeExpiredTokensBase ()
	{
		List<M> expiredMasterTokens = masterTokenService.findExpiredMasterTokens();
		List<T> expiredAuthenticationTokens = authenticationTokenService.findExpiredAuthenticationTokens();

		List<ExpiredTokenData> expiredTokenData = convertToExpiredTokenData(expiredMasterTokens, expiredAuthenticationTokens);

		masterTokenService.removeMasterTokens(expiredMasterTokens.stream().map(SecurityTokenDataBase::getId).collect(Collectors.toList()));
		authenticationTokenService.removeAuthenticationTokens(expiredAuthenticationTokens.stream().map(SecurityTokenDataBase::getId).collect(Collectors.toList()));

		return expiredTokenData;
	}

	private List<ExpiredTokenData> convertToExpiredTokenData (List<M> expiredMasterTokens, List<T> expiredAuthenticationTokens)
	{
		List<ExpiredTokenData> expiredTokensData = new ArrayList<>();
		expiredTokensData.addAll(expiredMasterTokens.stream().map(t -> new ExpiredTokenData(t.getToken(), t.getDeviceId(), t.getIp())).collect(Collectors.toList()));
		expiredTokensData.addAll(expiredAuthenticationTokens.stream().map(t -> new ExpiredTokenData(t.getToken(), t.getDeviceId(), t.getIp())).collect(Collectors.toList()));

		return expiredTokensData;
	}

	/**
	 * Create account data d.
	 *
	 * @param account the account
	 * @param additionalInformation the additional information
	 * @return the d
	 */
	protected abstract D createAccountData (A account, Map<String, Object> additionalInformation);

	/**
	 * Create additional information map.
	 *
	 * @param accountId the account id
	 * @return the map
	 */
	protected Map<String, Object> createAdditionalInformation (Long accountId)
	{
		return new HashMap<>();
	}
}
