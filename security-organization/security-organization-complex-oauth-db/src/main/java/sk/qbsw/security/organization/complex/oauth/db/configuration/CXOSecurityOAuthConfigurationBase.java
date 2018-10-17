package sk.qbsw.security.organization.complex.oauth.db.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapper;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.db.configuration.SecurityOAuthConfigurationBase;
import sk.qbsw.security.oauth.db.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.db.model.domain.MasterToken;
import sk.qbsw.security.organization.complex.core.service.mapper.CXOUserOutputDataMapperImpl;
import sk.qbsw.security.organization.complex.oauth.db.dao.CXOAuthenticationTokenJpaDaoImpl;
import sk.qbsw.security.organization.complex.oauth.db.dao.CXOMasterTokenJpaDaoImpl;

/**
 * The complex organization OAuth configuration base.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public abstract class CXOSecurityOAuthConfigurationBase extends SecurityOAuthConfigurationBase
{
	@Override
	@Bean
	public UserOutputDataMapper userOutputDataMapper ()
	{
		return new CXOUserOutputDataMapperImpl();
	}

    @Override
	@Bean
	public AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao ()
	{
		return new CXOAuthenticationTokenJpaDaoImpl();
	}

    @Override
	@Bean
	public MasterTokenDao<Account, MasterToken> masterTokenDao ()
	{
		return new CXOMasterTokenJpaDaoImpl();
	}
}
