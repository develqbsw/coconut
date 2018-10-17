package sk.qbsw.security.organization.simple.core.service.mapper;

import sk.qbsw.core.security.base.model.UserOutputData;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapper;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapperBase;
import sk.qbsw.security.organization.simple.base.model.SPOOrganizationOutputData;
import sk.qbsw.security.organization.simple.base.model.SPOUserOutputData;

/**
 * The simple organization user output data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class SPOUserOutputDataMapperImpl extends UserOutputDataMapperBase implements UserOutputDataMapper
{
	@Override
	protected UserOutputData instantiateUserOutputDataWithCustomAttributes (User user)
	{
		SPOUserOutputData spoUserOutputData = new SPOUserOutputData();
		if (user.getAccounts() != null && user.getAccounts().stream().findFirst().isPresent())
		{
			Organization organization = user.getAccounts().stream().findFirst().get().getOrganization();
			SPOOrganizationOutputData spoOrganizationOutputData = new SPOOrganizationOutputData(organization.getId(), organization.getName(), organization.getCode());

			spoUserOutputData.setOrganization(spoOrganizationOutputData);
		}

		return spoUserOutputData;
	}
}

