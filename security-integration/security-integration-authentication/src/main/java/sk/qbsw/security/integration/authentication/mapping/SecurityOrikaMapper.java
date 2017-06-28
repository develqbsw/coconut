package sk.qbsw.security.integration.authentication.mapping;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;
import sk.qbsw.security.api.authentication.client.model.CSAccountData;
import sk.qbsw.security.api.authentication.client.model.CSAuthenticationData;
import sk.qbsw.security.api.authentication.client.model.CSUserData;
import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.api.authentication.client.model.response.ReauthenticationResponseBody;
import sk.qbsw.security.core.model.domain.User;

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
		// user
		mapperFactory.classMap(User.class, CSUserData.class) //
			.byDefault() //
			.customize(new CustomMapper<User, CSUserData>()
			{
				@Override
				public void mapAtoB (User a, CSUserData b, MappingContext context)
				{
					b.setRoles(a.exportRoles());
				}
			}) //
			.register(); //
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
	}

	@Override
	public AuthenticationResponseBody mapToAuthenticationResponse (String masterToken, String authenticationToken, User user)
	{
		CSAuthenticationData authenticationData = CSAuthenticationData.newBuilder().masterToken(masterToken).authenticationToken(authenticationToken).build();
		CSUserData userData = mapperFactory.getMapperFacade().map(user, CSUserData.class);

		return AuthenticationResponseBody.newBuilder().authenticationData(authenticationData).userData(userData).build();
	}

	@Override
	public ReauthenticationResponseBody mapToReauthenticationResponse (String authenticationToken)
	{
		return ReauthenticationResponseBody.newBuilder().authenticationToken(authenticationToken).build();
	}

	@Override
	public CSAccountData mapUserToCSAccountData (User user)
	{
		return mapperFactory.getMapperFacade().map(user, CSAccountData.class);
	}
}
