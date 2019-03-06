package sk.qbsw.security.spring.iam.auth.ms.mapper;

import io.jsonwebtoken.Claims;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.base.mapper.UserDataMapperBase;
import sk.qbsw.security.spring.common.model.UserData;
import sk.qbsw.security.spring.iam.auth.common.configuration.InternalJwtConfiguration;

/**
 * The user output data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class UserDataMapperImpl extends UserDataMapperBase<Claims> implements UserDataMapper<Claims>
{
	private final InternalJwtConfiguration internalJwtConfiguration;

	/**
	 * Instantiates a new User data mapper.
	 *
	 * @param internalJwtConfiguration the internal jwt configuration
	 */
	public UserDataMapperImpl (InternalJwtConfiguration internalJwtConfiguration)
	{
		this.internalJwtConfiguration = internalJwtConfiguration;
	}

	@Override
	protected UserData instantiateUserDataWithCustomAttributes (Claims claims)
	{
		if (claims.containsKey(internalJwtConfiguration.getClaims().getUserId()))
		{
			UserData userData = new UserData();
			userData.setId(claims.get(internalJwtConfiguration.getClaims().getUserId(), Long.class));

			return userData;
		}
		else
		{
			return null;
		}
	}
}
