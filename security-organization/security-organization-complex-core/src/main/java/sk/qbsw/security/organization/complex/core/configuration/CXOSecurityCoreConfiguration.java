package sk.qbsw.security.organization.complex.core.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.core.configuration.SecurityCoreConfiguration;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.organization.complex.core.dao.*;


/**
 * The complex organization security core configuration.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class CXOSecurityCoreConfiguration extends SecurityCoreConfiguration
{
	@Bean
	@Override
	public AccountDao<Account> accountDao ()
	{
		return new CXOAccountJpaDao();
	}

	@Bean
	public CXOOrganizationDao cxoOrganizationDao ()
	{
		return new CXOOrganizationJpaDao();
	}

	@Bean
	public CXOUnitDao cxoUnitDao ()
	{
		return new CXOUnitJpaDao();
	}

	@Bean
	public CXOUserDao cxoUserDao ()
	{
		return new CXOUserJpaDao();
	}
}
