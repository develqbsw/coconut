package sk.qbsw.security.organization.complex.core.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.core.autoconfigure.SecurityCoreAutoConfigurationCommon;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.organization.complex.core.dao.*;


/**
 * The complex organization security core auto configuration.
 *
 * @author Tomas Leken
 * @version 2.6.0
 * @since 2.0.0
 */
@Configuration
@AutoConfigureAfter (SecurityCoreAutoConfigurationCommon.class)
public class CXOSecurityCoreAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public AccountDao<Account> accountDao ()
	{
		return new CXOAccountJpaDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public CXOOrganizationDao cxoOrganizationDao ()
	{
		return new CXOOrganizationJpaDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public CXOUnitDao cxoUnitDao ()
	{
		return new CXOUnitJpaDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public CXOUserDao cxoUserDao ()
	{
		return new CXOUserJpaDao();
	}
}
