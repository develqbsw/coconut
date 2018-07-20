package sk.qbsw.security.rest.oauth.api.base.mapper;

import org.springframework.stereotype.Component;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.oauth.model.VerificationTypes;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.CSGeneratedTokenData;
import sk.qbsw.security.rest.oauth.client.model.CSVerificationTypes;
import sk.qbsw.security.rest.oauth.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.rest.oauth.client.model.response.ReauthenticationResponseBody;
import sk.qbsw.security.rest.oauth.client.model.response.VerificationResponseBody;

import javax.annotation.PostConstruct;

/**
 * The security orika mapper.
 * 
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.18.0
 */
@Component
public class SecurityOrikaBaseMapper<T extends CSAccountData> extends BaseMapper implements SecurityMapper<T>
{
	/**
	 * Initialise the mapping.
	 */
	@PostConstruct
	private void initMapping ()
	{
		mapperFactory.classMap(AccountData.class, CSAccountData.class) //
			.byDefault() //
			.register();
		mapperFactory.classMap(GeneratedTokenData.class, CSGeneratedTokenData.class) //
			.byDefault() //
			.register();
		mapperFactory.classMap(VerificationTypes.class, CSVerificationTypes.class) //
			.byDefault() //
			.register();
	}

	@Override
	public AuthenticationResponseBody<T> mapToAuthenticationResponse (AuthenticationData authenticationData)
	{
		return mapperFactory.getMapperFacade().map(authenticationData, AuthenticationResponseBody.class);
	}

	@Override
	public ReauthenticationResponseBody mapToReauthenticationResponse (GeneratedTokenData authenticationTokenData)
	{
		return new ReauthenticationResponseBody(mapperFactory.getMapperFacade().map(authenticationTokenData, CSGeneratedTokenData.class));
	}

	@Override
	public VerificationResponseBody<T> mapToVerificationResponse (VerificationData verificationData)
	{
		return mapperFactory.getMapperFacade().map(verificationData, VerificationResponseBody.class);
	}
}
