package sk.qbsw.security.organization.rest.oauth.complex.api.mapper;

import org.springframework.stereotype.Component;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.organization.complex.base.model.CXOOrganizationOutputData;
import sk.qbsw.security.organization.complex.base.model.CXOUnitOutputData;
import sk.qbsw.security.organization.complex.base.model.CXOUserOutputData;
import sk.qbsw.security.organization.rest.oauth.complex.client.model.CSCXOOrganizationData;
import sk.qbsw.security.organization.rest.oauth.complex.client.model.CSCXOUnitData;
import sk.qbsw.security.organization.rest.oauth.complex.client.model.CSCXOUserData;
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
		mapperFactory.classMap(CXOUserOutputData.class, CSCXOUserData.class) //
			.byDefault() //
			.register();
		mapperFactory.classMap(CXOOrganizationOutputData.class, CSCXOOrganizationData.class) //
			.byDefault() //
			.register();
		mapperFactory.classMap(CXOUnitOutputData.class, CSCXOUnitData.class) //
			.byDefault() //
			.register();
	}
}
