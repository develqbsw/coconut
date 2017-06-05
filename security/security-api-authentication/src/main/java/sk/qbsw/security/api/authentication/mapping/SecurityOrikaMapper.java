package sk.qbsw.security.api.authentication.mapping;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import sk.qbsw.security.api.authentication.client.model.AuthenticationData;
import sk.qbsw.security.api.authentication.client.model.UserData;
import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponse;
import sk.qbsw.security.api.authentication.client.model.response.ReauthenticationResponse;
import sk.qbsw.security.core.model.domain.User;

/**
 * The security orika mapper.
 * 
 * @author Tomas Lauro
 *
 * @version 1.18.0
 * @since 1.18.0
 */
@Component
public class SecurityOrikaMapper extends BaseMapper implements SecurityMapper
{
	/**
	 * Inits the mapping.
	 */
	@PostConstruct
	private void initMapping ()
	{
		//user
		mapperFactory.classMap(User.class, UserData.class) //
			.byDefault() //
			.customize(new CustomMapper<User, UserData>()
			{
				@Override
				public void mapAtoB (User a, UserData b, MappingContext context)
				{
					b.setRoles(a.exportRoles());
				}
			}) //
			.register(); //
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.api.authentication.mapping.SecurityMapper#mapToAuthenticationResponse(java.lang.String, java.lang.String, sk.qbsw.security.core.model.domain.User)
	 */
	@Override
	public AuthenticationResponse mapToAuthenticationResponse (String masterToken, String authenticationToken, User user)
	{
		AuthenticationData authenticationData = AuthenticationData.create(masterToken, authenticationToken);
		UserData userData = mapperFactory.getMapperFacade().map(user, UserData.class);

		return AuthenticationResponse.create(authenticationData, userData);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.api.authentication.mapping.SecurityMapper#mapToReauthenticationResponse(java.lang.String)
	 */
	@Override
	public ReauthenticationResponse mapToReauthenticationResponse (String authenticationToken)
	{
		return ReauthenticationResponse.create(authenticationToken);
	}
}
