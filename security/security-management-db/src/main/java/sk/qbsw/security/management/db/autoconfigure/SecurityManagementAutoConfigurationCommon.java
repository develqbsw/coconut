package sk.qbsw.security.management.db.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.core.security.base.model.GroupOutputData;
import sk.qbsw.security.core.autoconfigure.SecurityCoreAutoConfigurationCommon;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;
import sk.qbsw.security.core.dao.*;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.service.mapper.*;
import sk.qbsw.security.core.service.signature.PasswordDigester;
import sk.qbsw.security.management.db.dao.GroupManageableDao;
import sk.qbsw.security.management.db.dao.GroupManageableJpaDao;
import sk.qbsw.security.management.db.mapper.GroupManageableOutputDataMapperImpl;
import sk.qbsw.security.management.db.model.domain.GroupManageable;
import sk.qbsw.security.management.db.service.*;
import sk.qbsw.security.management.model.GroupManageableOutputData;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.management.service.AccountPermissionManagementService;
import sk.qbsw.security.management.service.GroupManagementService;

/**
 * The security management auto configuration common.
 * 
 * @author Tomas Lauro
 * @version 2.6$.0
 * @since 2.0.0
 */
@Configuration
@AutoConfigureAfter (SecurityCoreAutoConfigurationCommon.class)
public class SecurityManagementAutoConfigurationCommon
{
	@Bean
	@ConditionalOnMissingBean
	public AccountInputDataMapper<AccountInputData, Account> accountInputDataMapper (UserInputDataMapper userInputDataMapper)
	{
		return new AccountInputDataMapperImpl(userInputDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper (UserOutputDataMapper userOutputDataMapper)
	{
		return new AccountOutputDataMapperImpl(userOutputDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public RoleOutputDataMapper roleOutputDataMapper ()
	{
		return new RoleOutputDataMapperImpl();
	}

	@Bean
	@ConditionalOnMissingBean (name = "groupOutputDataMapper")
	public GroupOutputDataMapper<GroupOutputData, Group> groupOutputDataMapper (RoleOutputDataMapper roleOutputDataMapper)
	{
		return new GroupOutputDataMapperImpl(roleOutputDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean (name = "groupManageableOutputDataMapper")
	public GroupOutputDataMapper<GroupManageableOutputData, GroupManageable> groupManageableOutputDataMapper (RoleOutputDataMapper roleOutputDataMapper, UserOutputDataMapper userOutputDataMapper)
	{
		return new GroupManageableOutputDataMapperImpl(roleOutputDataMapper, userOutputDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public GroupManageableDao groupManageableDao ()
	{
		return new GroupManageableJpaDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public AccountCredentialManagementService accountCredentialManagementService (AccountDao<Account> accountDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester, SecurityCoreConfigurator securityCoreConfigurator)
	{
		return new AccountCredentialManagementServiceImpl(accountDao, authenticationParamsDao, digester, securityCoreConfigurator);
	}

	@Bean
	@ConditionalOnMissingBean
	public AccountPermissionManagementService accountPermissionManagementService (AccountDao<Account> accountDao, GroupDao groupDao, UnitDao unitDao, AccountUnitGroupDao accountUnitGroupDao)
	{
		return new AccountPermissionManagementServiceImpl(accountDao, groupDao, unitDao, accountUnitGroupDao);
	}

	@Bean
	@ConditionalOnMissingBean
	public GroupManagementService groupManagementService (GroupManageableDao groupManageableDao, RoleDao roleDao, UserDao userDao, GroupOutputDataMapper<GroupManageableOutputData, GroupManageable> groupManageableOutputDataMapper)
	{
		return new GroupManageableServiceImpl(groupManageableDao, roleDao, userDao, groupManageableOutputDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public LoginBlockingService loginBlockingService (BlockedLoginDao blockedLoginJpaDao, AccountDao accountDao)
	{
		return new LoginBlockingServiceImpl(blockedLoginJpaDao, accountDao);
	}
}
