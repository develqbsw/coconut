package sk.qbsw.security.organization.simple.oauth.mapper;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountMapper;
import sk.qbsw.security.core.service.mapper.AccountMapperBase;
import sk.qbsw.security.organization.simple.oauth.model.OrganizationData;
import sk.qbsw.security.organization.simple.oauth.model.SimpleOrganizationAccountData;

/**
 * The account mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class AccountMapperImpl extends AccountMapperBase<SimpleOrganizationAccountData, Account> implements AccountMapper<SimpleOrganizationAccountData, Account>
{
	@Override
	protected SimpleOrganizationAccountData instantiateWithCustomAttributes (Account account)
	{
		OrganizationData organizationData = new OrganizationData();
		organizationData.setId(account.getOrganization().getId());
		organizationData.setName(account.getOrganization().getName());
		organizationData.setCode(account.getOrganization().getCode());

		SimpleOrganizationAccountData accountData = new SimpleOrganizationAccountData();
		accountData.setOrganization(organizationData);

		return accountData;
	}
}
