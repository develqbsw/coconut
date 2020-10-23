package sk.qbsw.security.oauth.db.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.authentication.db.autoconfigure.SecurityAuthenticationAutoConfiguration;
import sk.qbsw.security.core.autoconfigure.SecurityCoreAutoConfiguration;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapper;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapperImpl;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.db.dao.AuthenticationTokenJpaDaoImpl;
import sk.qbsw.security.oauth.db.dao.MasterTokenJpaDaoImpl;
import sk.qbsw.security.oauth.db.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.db.model.domain.MasterToken;

/**
 * The security OAuth auto configuration base.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.2
 */
@Configuration
@AutoConfigureAfter ({SecurityCoreAutoConfiguration.class, SecurityAuthenticationAutoConfiguration.class, SecurityOAuthAutoConfigurationCommon.class})
public class SecurityOAuthAutoConfigurationBase
{
	@Bean
	@ConditionalOnMissingBean
	public UserOutputDataMapper userOutputDataMapper ()
	{
		return new UserOutputDataMapperImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao ()
	{
		return new AuthenticationTokenJpaDaoImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public MasterTokenDao<Account, MasterToken> masterTokenDao ()
	{
		return new MasterTokenJpaDaoImpl();
	}
}
