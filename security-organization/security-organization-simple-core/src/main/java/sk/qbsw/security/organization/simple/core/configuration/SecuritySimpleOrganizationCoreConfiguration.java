package sk.qbsw.security.organization.simple.core.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurationBase;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AccountJpaDao;
import sk.qbsw.security.core.model.domain.Account;


/**
 * The security simple organization core configuration.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class SecuritySimpleOrganizationCoreConfiguration extends SecurityCoreConfigurationBase<Account>
{
	@Bean
	@Override
	public AccountDao<Account> accountDao ()
	{
		return new AccountJpaDao();
	}
}
