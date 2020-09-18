package sk.qbsw.security.organization.simple.oauth.db.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.core.autoconfigure.SecurityCoreAutoConfiguration;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapper;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.db.autoconfigure.SecurityOAuthAutoConfigurationCommon;
import sk.qbsw.security.oauth.db.dao.AuthenticationTokenJpaDaoImpl;
import sk.qbsw.security.oauth.db.dao.MasterTokenJpaDaoImpl;
import sk.qbsw.security.oauth.db.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.db.model.domain.MasterToken;
import sk.qbsw.security.organization.simple.authentication.db.autoconfigure.SPOSecurityAuthenticationAutoConfiguration;
import sk.qbsw.security.organization.simple.core.service.mapper.SPOUserOutputDataMapperImpl;

/**
 * The simple organization security OAuth auto configuration base.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.0.0
 */
@Configuration
@AutoConfigureAfter ({SecurityCoreAutoConfiguration.class, SPOSecurityAuthenticationAutoConfiguration.class, SecurityOAuthAutoConfigurationCommon.class})
public class SPOSecurityOAuthAutoConfigurationBase
{
	@Bean
	@ConditionalOnMissingBean
	public UserOutputDataMapper userOutputDataMapper ()
	{
		return new SPOUserOutputDataMapperImpl();
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
