package sk.qbsw.security.integration.authentication.mapping;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;
import sk.qbsw.security.api.authentication.client.model.CSAccountData;
import sk.qbsw.security.api.authentication.client.model.CSGeneratedTokenData;
import sk.qbsw.security.api.authentication.client.model.CSVerificationTypes;
import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.api.authentication.client.model.response.ReauthenticationResponseBody;
import sk.qbsw.security.api.authentication.client.model.response.VerificationResponseBody;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.oauth.model.VerificationTypes;

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
		mapperFactory.classMap(User.class, CSAccountData.class) //
			.byDefault() //
			.customize(new CustomMapper<User, CSAccountData>()
			{
				@Override
				public void mapAtoB (User a, CSAccountData b, MappingContext context)
				{
					b.setRoles(a.exportRoles());
				}
			}) //
			.register();
		mapperFactory.classMap(GeneratedTokenData.class, CSGeneratedTokenData.class) //
			.byDefault() //
			.register();
		mapperFactory.classMap(VerificationTypes.class, CSVerificationTypes.class) //
			.byDefault() //
			.register();
	}

	@Override
	public AuthenticationResponseBody mapToAuthenticationResponse (AuthenticationData authenticationData)
	{
		CSAccountData accountData = mapperFactory.getMapperFacade().map(authenticationData.getAccountData().getUser(), CSAccountData.class);
		accountData.setAdditionalInformation(authenticationData.getAccountData().getAdditionalInformation());

		CSGeneratedTokenData masterTokenData = mapperFactory.getMapperFacade().map(authenticationData.getMasterTokenData(), CSGeneratedTokenData.class);
		CSGeneratedTokenData authenticationTokenData = mapperFactory.getMapperFacade().map(authenticationData.getAuthenticationTokenData(), CSGeneratedTokenData.class);

		return new AuthenticationResponseBody(masterTokenData, authenticationTokenData, accountData);
	}

	@Override
	public ReauthenticationResponseBody mapToReauthenticationResponse (GeneratedTokenData authenticationTokenData)
	{
		return new ReauthenticationResponseBody(mapperFactory.getMapperFacade().map(authenticationTokenData, CSGeneratedTokenData.class));
	}

	@Override
	public VerificationResponseBody mapToVerificationResponse (VerificationData verificationData)
	{
		CSAccountData accountData = mapperFactory.getMapperFacade().map(verificationData.getAccountData().getUser(), CSAccountData.class);
		accountData.setAdditionalInformation(verificationData.getAccountData().getAdditionalInformation());

		return new VerificationResponseBody(accountData, mapperFactory.getMapperFacade().map(verificationData.getVerificationType(), CSVerificationTypes.class));
	}
}
