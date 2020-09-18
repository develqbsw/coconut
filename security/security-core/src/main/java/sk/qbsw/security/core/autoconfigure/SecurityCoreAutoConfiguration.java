package sk.qbsw.security.core.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AccountJpaDao;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The security core auto configuration.
 * 
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.0.0
 */
@Configuration
@AutoConfigureAfter (SecurityCoreAutoConfigurationCommon.class)
public class SecurityCoreAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public AccountDao<Account> accountDao ()
	{
		return new AccountJpaDao();
	}
}
