package sk.qbsw.security.organization.complex.management.db.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.core.service.mapper.UserInputDataMapper;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapper;
import sk.qbsw.security.management.db.autoconfigure.SecurityManagementAutoConfigurationCommon;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.organization.complex.core.autoconfigure.CXOSecurityCoreAutoConfiguration;
import sk.qbsw.security.organization.complex.core.dao.CXOOrganizationDao;
import sk.qbsw.security.organization.complex.core.dao.CXOUnitDao;
import sk.qbsw.security.organization.complex.core.dao.CXOUserDao;
import sk.qbsw.security.organization.complex.core.service.mapper.CXOUserInputDataMapperImpl;
import sk.qbsw.security.organization.complex.core.service.mapper.CXOUserOutputDataMapperImpl;
import sk.qbsw.security.organization.complex.management.db.service.CXOAccountManagementServiceImpl;
import sk.qbsw.security.organization.complex.management.db.service.CXOOrganizationServiceImpl;
import sk.qbsw.security.organization.complex.management.db.service.CXOUnitServiceImpl;
import sk.qbsw.security.organization.complex.management.db.service.CXOUserServiceImpl;
import sk.qbsw.security.organization.complex.management.service.CXOOrganizationService;
import sk.qbsw.security.organization.complex.management.service.CXOUnitService;
import sk.qbsw.security.organization.complex.management.service.CXOUserService;

/**
 * The complex organization security management auto configuration.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.0.0
 */
@Configuration
@AutoConfigureAfter ({CXOSecurityCoreAutoConfiguration.class, SecurityManagementAutoConfigurationCommon.class})
public class CXOSecurityManagementAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public UserInputDataMapper userInputDataMapper ()
	{
		return new CXOUserInputDataMapperImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public UserOutputDataMapper userOutputDataMapper ()
	{
		return new CXOUserOutputDataMapperImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public AccountManagementService<AccountInputData, AccountData> accountManagementService (AccountDao<Account> accountDao, UserDao userDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<AccountInputData, Account> accountAccountInputDataMapper, AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper, CXOUnitDao unitDao)
	{
		return new CXOAccountManagementServiceImpl(accountDao, userDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper, unitDao);
	}

	@Bean
	@ConditionalOnMissingBean
	public CXOOrganizationService organizationService (CXOOrganizationDao organizationComplexDao)
	{
		return new CXOOrganizationServiceImpl(organizationComplexDao);
	}

	@Bean
	@ConditionalOnMissingBean
	public CXOUnitService unitService (CXOUnitDao unitDao, CXOUserDao userDao)
	{
		return new CXOUnitServiceImpl(unitDao, userDao);
	}

	@Bean
	@ConditionalOnMissingBean
	public CXOUserService userService (CXOUserDao userDao)
	{
		return new CXOUserServiceImpl(userDao);
	}
}
