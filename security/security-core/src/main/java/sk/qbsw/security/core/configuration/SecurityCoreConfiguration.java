package sk.qbsw.security.core.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AccountJpaDao;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The security core configuration.
 * 
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class SecurityCoreConfiguration extends SecurityCoreConfigurationBase<Account>
{
	@Bean
	@Override
	public AccountDao<Account> accountDao ()
	{
		return new AccountJpaDao();
	}
}
