package sk.qbsw.security.organization.complex.core.service.mapper;

import sk.qbsw.organization.complex.core.model.domain.User;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapperBase;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountInputData;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;

/**
 * The complex organization account mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class ComplexOrganizationInputDataAccountMapperImpl extends AccountInputDataMapperBase<ComplexOrganizationAccountInputData, UserAccount> implements AccountInputDataMapper<ComplexOrganizationAccountInputData, UserAccount>
{
	@Override
	protected UserAccount instantiateAccountWithCustomAttributes (ComplexOrganizationAccountInputData accountInputData)
	{
		User user = new User();
		user.setId(accountInputData.getUserId());

		UserAccount userAccount = new UserAccount();
		userAccount.setUser(user);

		return userAccount;
	}
}
