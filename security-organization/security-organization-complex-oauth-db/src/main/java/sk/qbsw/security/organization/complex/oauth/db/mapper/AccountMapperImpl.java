package sk.qbsw.security.organization.complex.oauth.db.mapper;

import sk.qbsw.organization.complex.core.model.domain.Organization;
import sk.qbsw.organization.complex.core.model.domain.Unit;
import sk.qbsw.security.core.service.mapper.AccountMapper;
import sk.qbsw.security.core.service.mapper.AccountMapperBase;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;
import sk.qbsw.security.organization.complex.oauth.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.oauth.model.OrganizationData;
import sk.qbsw.security.organization.complex.oauth.model.UnitData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The account mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class AccountMapperImpl extends AccountMapperBase<ComplexOrganizationAccountData, UserAccount> implements AccountMapper<ComplexOrganizationAccountData, UserAccount>
{
	@Override
	protected ComplexOrganizationAccountData instantiateWithCustomAttributes (UserAccount account)
	{
		Map<Organization, List<Unit>> unitsByOrganization = account.getUser().getUnits().stream().collect(Collectors.groupingBy(Unit::getOrganization));
		List<OrganizationData> organizationData = unitsByOrganization.entrySet().stream().map(e -> new OrganizationData(e.getKey().getId(), e.getKey().getName(), e.getKey().getCode(), e.getValue().stream().map(u -> new UnitData(u.getId(), u.getName(), u.getCode())).collect(Collectors.toList()))).collect(Collectors.toList());

		ComplexOrganizationAccountData accountData = new ComplexOrganizationAccountData();
		accountData.setUserId(account.getUser().getId());
		accountData.setOrganizations(organizationData);

		return accountData;
	}
}
