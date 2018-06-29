package sk.qbsw.organization.core.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.organization.core.dao.OrganizationDao;
import sk.qbsw.organization.core.dao.UnitDao;
import sk.qbsw.organization.core.dao.UnitJpaDao;
import sk.qbsw.organization.core.dao.OrganizationJpaDao;
import sk.qbsw.organization.core.dao.UserDao;
import sk.qbsw.organization.core.dao.UserJpaDao;

/**
 * The type Organization core configuration.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class OrganizationCoreConfiguration
{
	@Bean
	public OrganizationDao organizationDao ()
	{
		return new OrganizationJpaDao();
	}

	@Bean
	public UnitDao unitDao ()
	{
		return new UnitJpaDao();
	}

	@Bean
	public UserDao userDao ()
	{
		return new UserJpaDao();
	}
}
