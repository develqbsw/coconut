package sk.qbsw.security.organization.rest.oauth.simple.api.mapper;

import org.springframework.stereotype.Component;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.organization.rest.oauth.simple.client.model.CSSPOUserData;
import sk.qbsw.security.organization.simple.base.model.SPOUserOutputData;
import sk.qbsw.security.rest.oauth.api.base.mapper.SecurityOrikaMapperBase;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;

/**
 * The type Security orika mapper.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
@Component
public class SecurityOrikaMapper extends SecurityOrikaMapperBase<AccountData, CSAccountData>
{
	@Override
	protected void initUserMapping ()
	{
		mapperFactory.classMap(SPOUserOutputData.class, CSSPOUserData.class) //
			.byDefault() //
			.register();
	}
}
