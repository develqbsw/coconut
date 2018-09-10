package sk.qbsw.security.organization.complex.management.db.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import sk.qbsw.organization.complex.management.configuration.OrganizationManagementConfiguration;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.core.service.signature.PasswordDigester;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountInputData;
import sk.qbsw.security.organization.complex.core.configuration.SecurityComplexOrganizationCoreConfiguration;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;
import sk.qbsw.security.organization.complex.core.service.mapper.ComplexOrganizationInputDataAccountMapperImpl;
import sk.qbsw.security.organization.complex.core.service.mapper.ComplexOrganizationOutputDataAccountMapperImpl;
import sk.qbsw.security.organization.complex.management.db.service.ComplexOrganizationAccountCredentialManagementServiceImpl;
import sk.qbsw.security.organization.complex.management.db.service.ComplexOrganizationAccountManagementServiceImpl;

/**
 * The security complex organization management configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Import ({OrganizationManagementConfiguration.class})
public class SecurityComplexOrganizationManagementConfiguration extends SecurityComplexOrganizationCoreConfiguration
{
	@Bean
	public AccountInputDataMapper<ComplexOrganizationAccountInputData, UserAccount> accountInputDataMapper ()
	{
		return new ComplexOrganizationInputDataAccountMapperImpl();
	}

	@Bean
	public AccountOutputDataMapper<ComplexOrganizationAccountData, UserAccount> accountOutputDataMapper ()
	{
		return new ComplexOrganizationOutputDataAccountMapperImpl();
	}

	@Bean
	public AccountCredentialManagementService accountCredentialManagementService (AccountDao<UserAccount> accountDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester, SecurityCoreConfigurator securityCoreConfigurator)
	{
		return new ComplexOrganizationAccountCredentialManagementServiceImpl(accountDao, authenticationParamsDao, digester, securityCoreConfigurator);
	}

	@Bean
	public AccountManagementService<ComplexOrganizationAccountInputData, ComplexOrganizationAccountData> accountManagementService (AccountDao<UserAccount> accountDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<ComplexOrganizationAccountInputData, UserAccount> accountAccountInputDataMapper, AccountOutputDataMapper<ComplexOrganizationAccountData, UserAccount> accountOutputDataMapper)
	{
		return new ComplexOrganizationAccountManagementServiceImpl(accountDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper);
	}
}
