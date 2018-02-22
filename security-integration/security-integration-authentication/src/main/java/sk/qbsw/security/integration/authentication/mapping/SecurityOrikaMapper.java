package sk.qbsw.security.integration.authentication.mapping;

import org.springframework.stereotype.Component;
import sk.qbsw.security.api.authentication.client.model.CSAccountData;
import sk.qbsw.security.api.authentication.client.model.CSGeneratedTokenData;
import sk.qbsw.security.api.authentication.client.model.CSSimplifiedOrganization;
import sk.qbsw.security.api.authentication.client.model.CSVerificationTypes;
import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.api.authentication.client.model.response.ReauthenticationResponseBody;
import sk.qbsw.security.api.authentication.client.model.response.VerificationResponseBody;
import sk.qbsw.security.oauth.model.*;

import javax.annotation.PostConstruct;

/**
 * The security orika mapper.
 * 
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
@Component
public class SecurityOrikaMapper extends BaseMapper implements SecurityMapper
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
		mapperFactory.classMap(OrganizationData.class, CSSimplifiedOrganization.class) //
			.byDefault() //
			.register();
		mapperFactory.classMap(VerificationTypes.class, CSVerificationTypes.class) //
			.byDefault() //
			.register();
	}

	@Override
	public AuthenticationResponseBody mapToAuthenticationResponse (AuthenticationData authenticationData)
	{
		return mapperFactory.getMapperFacade().map(authenticationData, AuthenticationResponseBody.class);
	}

	@Override
	public ReauthenticationResponseBody mapToReauthenticationResponse (GeneratedTokenData authenticationTokenData)
	{
		return new ReauthenticationResponseBody(mapperFactory.getMapperFacade().map(authenticationTokenData, CSGeneratedTokenData.class));
	}

	@Override
	public VerificationResponseBody mapToVerificationResponse (VerificationData verificationData)
	{
		return mapperFactory.getMapperFacade().map(verificationData, VerificationResponseBody.class);
	}
}
