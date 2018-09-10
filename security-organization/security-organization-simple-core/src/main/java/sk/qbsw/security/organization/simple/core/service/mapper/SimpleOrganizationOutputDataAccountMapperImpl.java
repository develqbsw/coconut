package sk.qbsw.security.organization.simple.core.service.mapper;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapperBase;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationData;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountData;

/**
 * The simple organization output data account mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class SimpleOrganizationOutputDataAccountMapperImpl extends AccountOutputDataMapperBase<SimpleOrganizationAccountData, Account> implements AccountOutputDataMapper<SimpleOrganizationAccountData, Account>
{
	@Override
	protected SimpleOrganizationAccountData instantiateAccountOutputDataWithCustomAttributes (Account account)
	{
		SimpleOrganizationAccountData accountData = new SimpleOrganizationAccountData();
		accountData.setOrganization(new SimpleOrganizationData(account.getOrganization().getId(), account.getOrganization().getName(), account.getOrganization().getCode()));

		return accountData;
	}
}
