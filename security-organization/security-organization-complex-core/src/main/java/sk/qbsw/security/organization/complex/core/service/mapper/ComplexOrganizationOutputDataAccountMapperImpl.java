package sk.qbsw.security.organization.complex.core.service.mapper;

import org.apache.commons.collections.CollectionUtils;
import sk.qbsw.organization.complex.core.model.domain.Organization;
import sk.qbsw.organization.complex.core.model.domain.Unit;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapperBase;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationData;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationUnitData;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The complex organization account mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class ComplexOrganizationOutputDataAccountMapperImpl extends AccountOutputDataMapperBase<ComplexOrganizationAccountData, UserAccount> implements AccountOutputDataMapper<ComplexOrganizationAccountData, UserAccount>
{
	@Override
	protected ComplexOrganizationAccountData instantiateAccountOutputDataWithCustomAttributes (UserAccount account)
	{
		ComplexOrganizationAccountData accountData = new ComplexOrganizationAccountData();
		accountData.setUserId(account.getUser().getId());

		if (!CollectionUtils.isEmpty(account.getUser().getUnits()))
		{
			Map<Organization, List<Unit>> unitsByOrganization = account.getUser().getUnits().stream().collect(Collectors.groupingBy(Unit::getOrganization));
			List<ComplexOrganizationData> organizationData = unitsByOrganization.entrySet().stream().map(e -> new ComplexOrganizationData(e.getKey().getId(), e.getKey().getName(), e.getKey().getCode(), e.getValue().stream().map(u -> new ComplexOrganizationUnitData(u.getId(), u.getName(), u.getCode())).collect(Collectors.toList()))).collect(Collectors.toList());

			accountData.setOrganizations(organizationData);
		}

		return accountData;
	}
}
