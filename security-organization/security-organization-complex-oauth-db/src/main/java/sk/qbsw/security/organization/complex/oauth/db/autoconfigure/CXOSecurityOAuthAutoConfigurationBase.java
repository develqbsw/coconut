package sk.qbsw.security.organization.complex.oauth.db.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapper;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.db.autoconfigure.SecurityOAuthAutoConfigurationCommon;
import sk.qbsw.security.oauth.db.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.db.model.domain.MasterToken;
import sk.qbsw.security.organization.complex.authentication.db.autoconfigure.CXOSecurityAuthenticationAutoConfiguration;
import sk.qbsw.security.organization.complex.core.autoconfigure.CXOSecurityCoreAutoConfiguration;
import sk.qbsw.security.organization.complex.core.service.mapper.CXOUserOutputDataMapperImpl;
import sk.qbsw.security.organization.complex.oauth.db.dao.CXOAuthenticationTokenJpaDaoImpl;
import sk.qbsw.security.organization.complex.oauth.db.dao.CXOMasterTokenJpaDaoImpl;

/**
 * The complex organization OAuth auto configuration base.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.0.0
 */
@Configuration
@AutoConfigureAfter ({CXOSecurityCoreAutoConfiguration.class, CXOSecurityAuthenticationAutoConfiguration.class, SecurityOAuthAutoConfigurationCommon.class})
public class CXOSecurityOAuthAutoConfigurationBase
{
	@Bean
	@ConditionalOnMissingBean
	public UserOutputDataMapper userOutputDataMapper ()
	{
		return new CXOUserOutputDataMapperImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao ()
	{
		return new CXOAuthenticationTokenJpaDaoImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public MasterTokenDao<Account, MasterToken> masterTokenDao ()
	{
		return new CXOMasterTokenJpaDaoImpl();
	}
}
