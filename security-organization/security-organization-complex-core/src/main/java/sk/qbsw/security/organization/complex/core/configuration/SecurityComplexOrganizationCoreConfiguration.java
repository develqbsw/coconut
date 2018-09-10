package sk.qbsw.security.organization.complex.core.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurationBase;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.organization.complex.core.dao.UserAccountJpaDao;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;


/**
 * The security complex organization core configuration.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class SecurityComplexOrganizationCoreConfiguration extends SecurityCoreConfigurationBase<UserAccount>
{
	@Bean
	@Override
	public AccountDao<UserAccount> accountDao ()
	{
		return new UserAccountJpaDao();
	}
}
