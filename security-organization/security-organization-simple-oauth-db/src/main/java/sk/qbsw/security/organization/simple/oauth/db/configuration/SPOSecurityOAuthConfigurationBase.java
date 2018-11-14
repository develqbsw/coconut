package sk.qbsw.security.organization.simple.oauth.db.configuration;

import org.springframework.context.annotation.Bean;

import sk.qbsw.security.core.service.mapper.UserOutputDataMapper;
import sk.qbsw.security.oauth.db.configuration.SecurityOAuthConfigurationBase;
import sk.qbsw.security.organization.simple.core.service.mapper.SPOUserOutputDataMapperImpl;

/**
 * The simple organization OAuth configuration base.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class SPOSecurityOAuthConfigurationBase extends SecurityOAuthConfigurationBase
{
	@Override
	@Bean
	public UserOutputDataMapper userOutputDataMapper ()
	{
		return new SPOUserOutputDataMapperImpl();
	}
}
