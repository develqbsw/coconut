package sk.qbsw.security.organization.simple.management.db.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapper;
import sk.qbsw.security.management.db.configuration.SecurityManagementConfiguration;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.organization.simple.core.service.mapper.SPOUserOutputDataMapperImpl;
import sk.qbsw.security.organization.simple.management.SPOAccountManagementService;
import sk.qbsw.security.organization.simple.management.db.service.SPOAccountManagementServiceImpl;

/**
 * The simple organization security management configuration.
 * 
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 2.2.0
 * @since 2.0.0
 */
public class SPOSecurityManagementConfiguration extends SecurityManagementConfiguration
{
	@Override
	@Bean
	public UserOutputDataMapper userOutputDataMapper ()
	{
		return new SPOUserOutputDataMapperImpl();
	}

	@Bean
	public SPOAccountManagementService<AccountInputData, AccountData> accountManagementService (AccountDao<Account> accountDao, UserDao userDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<AccountInputData, Account> accountAccountInputDataMapper, AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper)
	{
		return new SPOAccountManagementServiceImpl(accountDao, userDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper);
	}
}
