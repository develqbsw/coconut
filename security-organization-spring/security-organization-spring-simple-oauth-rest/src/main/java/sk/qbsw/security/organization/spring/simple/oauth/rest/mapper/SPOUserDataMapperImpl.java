package sk.qbsw.security.organization.spring.simple.oauth.rest.mapper;

import sk.qbsw.security.organization.rest.oauth.simple.client.model.CSSPOUserData;
import sk.qbsw.security.organization.spring.simple.common.model.SPOOrganization;
import sk.qbsw.security.organization.spring.simple.common.model.SPOUserData;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.base.mapper.UserDataMapperBase;
import sk.qbsw.security.spring.common.model.UserData;

/**
 * The simple organization user data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class SPOUserDataMapperImpl extends UserDataMapperBase<CSSPOUserData> implements UserDataMapper<CSSPOUserData>
{
	@Override
	protected UserData instantiateUserDataWithCustomAttributes (CSSPOUserData user)
	{
		SPOOrganization organization = new SPOOrganization( //
			user.getOrganization().getId(), //
			user.getOrganization().getName(), //
			user.getOrganization().getCode() //
		);

		return new SPOUserData( //
			user.getId(), //
			organization //
		);
	}
}
