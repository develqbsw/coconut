package sk.qbsw.security.organization.core.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.organization.core.dao.UserAccountDao;
import sk.qbsw.security.organization.core.dao.UserAccountJpaDao;

/**
 * The type Security organization core configuration.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class SecurityOrganizationCoreConfiguration
{
	@Bean
	public UserAccountDao userAccountDao ()
	{
		return new UserAccountJpaDao();
	}
}
