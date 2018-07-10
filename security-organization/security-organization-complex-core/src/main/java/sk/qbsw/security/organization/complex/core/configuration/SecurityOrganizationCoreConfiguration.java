package sk.qbsw.security.organization.complex.core.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.organization.complex.core.dao.UserAccountDao;
import sk.qbsw.security.organization.complex.core.dao.UserAccountJpaDao;


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
