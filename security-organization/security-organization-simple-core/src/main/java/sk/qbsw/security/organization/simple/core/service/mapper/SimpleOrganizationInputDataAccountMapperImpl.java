package sk.qbsw.security.organization.simple.core.service.mapper;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapperBase;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountInputData;

/**
 * The simple organization account mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class SimpleOrganizationInputDataAccountMapperImpl extends AccountInputDataMapperBase<SimpleOrganizationAccountInputData, Account> implements AccountInputDataMapper<SimpleOrganizationAccountInputData, Account>
{
	@Override
	protected Account instantiateAccountWithCustomAttributes (SimpleOrganizationAccountInputData accountInputData)
	{
		Organization organization = new Organization();
		organization.setId(accountInputData.getOrganizationId());

		Account account = new Account();
		account.setOrganization(organization);

		return account;
	}
}
