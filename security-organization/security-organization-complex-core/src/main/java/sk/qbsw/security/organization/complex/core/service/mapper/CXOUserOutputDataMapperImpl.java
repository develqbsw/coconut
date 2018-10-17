package sk.qbsw.security.organization.complex.core.service.mapper;

import org.apache.commons.collections.CollectionUtils;
import sk.qbsw.core.security.base.model.UserOutputData;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapper;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapperBase;
import sk.qbsw.security.organization.complex.base.model.CXOOrganizationOutputData;
import sk.qbsw.security.organization.complex.base.model.CXOUserOutputData;
import sk.qbsw.security.organization.complex.base.model.CXOUnitOutputData;
import sk.qbsw.security.organization.complex.core.model.domain.CXOOrganization;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUser;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUnit;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The complex organization user output data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class CXOUserOutputDataMapperImpl extends UserOutputDataMapperBase implements UserOutputDataMapper
{
	@Override
	protected UserOutputData instantiateUserOutputDataWithCustomAttributes (User user)
	{
		CXOUser cxoUser = (CXOUser) user;
		CXOUserOutputData cxoUserOutputData = new CXOUserOutputData();

		if (!CollectionUtils.isEmpty(cxoUser.getUnits()))
		{
			Map<CXOOrganization, List<CXOUnit>> unitsByOrganization = cxoUser.getUnits().stream().collect(Collectors.groupingBy(CXOUnit::getOrganization));
			List<CXOOrganizationOutputData> organizationData = unitsByOrganization.entrySet().stream().map(e -> new CXOOrganizationOutputData(e.getKey().getId(), e.getKey().getName(), e.getKey().getCode(), e.getValue().stream().map(u -> new CXOUnitOutputData(u.getId(), u.getName(), u.getCode())).collect(Collectors.toList()))).collect(Collectors.toList());

			cxoUserOutputData.setOrganizations(organizationData);
		}


		return cxoUserOutputData;
	}
}

