package sk.qbsw.security.rest.oauth.api.base.mapper;

import ma.glasnost.orika.metadata.TypeBuilder;
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
 * @param <D> the type parameter
 * @param <S> the type parameter
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.18.0
 */
public abstract class SecurityOrikaMapperBase<D extends AccountData, S extends CSAccountData>extends MapperBase implements SecurityMapper<D, S>
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
	public AuthenticationResponseBody<S> mapToAuthenticationResponse (AuthenticationData<D> authenticationData)
	{
		return mapperFactory.getMapperFacade().map(authenticationData, //
			new TypeBuilder<AuthenticationData<D>>()
			{
			}.build(), //
			new TypeBuilder<AuthenticationResponseBody<S>>()
			{
			}.build());
	}

	@Override
	public ReauthenticationResponseBody mapToReauthenticationResponse (GeneratedTokenData authenticationTokenData)
	{
		return new ReauthenticationResponseBody(mapperFactory.getMapperFacade().map(authenticationTokenData, CSGeneratedTokenData.class));
	}

	@Override
	public VerificationResponseBody<S> mapToVerificationResponse (VerificationData<D> verificationData)
	{
		return mapperFactory.getMapperFacade().map(verificationData, //
			new TypeBuilder<VerificationData<D>>()
			{
			}.build(), //
			new TypeBuilder<VerificationResponseBody<S>>()
			{
			}.build());
	}
}
