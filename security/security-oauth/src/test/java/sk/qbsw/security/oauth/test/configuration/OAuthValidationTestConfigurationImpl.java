package sk.qbsw.security.oauth.test.configuration;

import ESystemParameters.ECoreSystemParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.qbsw.core.configuration.model.domain.CSystemParameter;
import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.security.oauth.configuration.OAuthValidationConfiguration;

import javax.annotation.PostConstruct;

/**
 * The oauth validation configuration.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class OAuthValidationTestConfigurationImpl implements OAuthValidationConfiguration
{
	/** The log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OAuthValidationTestConfigurationImpl.class);

	private final ISystemParameterService systemParameterService;

	private boolean masterTokenIpIgnored;

	private boolean authenticationTokenIpIgnored;

	/** The master token expire limit in hours. */
	private Integer masterTokenExpireLimit;

	/** The authentication token expire limit in hours. */
	private Integer authenticationTokenExpireLimit;

	/** The change limit in hours - ak je < 1, tak ignorovat, beriem to tak, ze je to vypnute lebo to niekto chcel vypnut. */
	private Integer masterTokenChangeLimit;

	/** The authentication token change limit. */
	private Integer authenticationTokenChangeLimit;

	public OAuthValidationTestConfigurationImpl (ISystemParameterService systemParameterService)
	{
		this.systemParameterService = systemParameterService;
	}

	@PostConstruct
	public void initProperties ()
	{
		masterTokenIpIgnored = false;
		authenticationTokenIpIgnored = false;

		CSystemParameter masterTokenExpireLimitParam = systemParameterService.findByName(ECoreSystemParameter.MASTER_TOKEN_EXPIRE_LIMIT.getParameterName());
		if (masterTokenExpireLimitParam == null || masterTokenExpireLimitParam.getIntegerValue() == null || masterTokenExpireLimitParam.getIntegerValue() < 1)
		{
			masterTokenExpireLimit = null;
		}
		else
		{
			masterTokenExpireLimit = masterTokenExpireLimitParam.getIntegerValue();
		}

		CSystemParameter authenticationTokenExpireLimitParam = systemParameterService.findByName(ECoreSystemParameter.AUTHENTICATION_TOKEN_EXPIRE_LIMIT.getParameterName());
		if (authenticationTokenExpireLimitParam == null || authenticationTokenExpireLimitParam.getIntegerValue() == null || authenticationTokenExpireLimitParam.getIntegerValue() < 1)
		{
			authenticationTokenExpireLimit = null;
		}
		else
		{
			authenticationTokenExpireLimit = authenticationTokenExpireLimitParam.getIntegerValue();
		}

		CSystemParameter masterTokenChangeLimitParam = systemParameterService.findByName(ECoreSystemParameter.MASTER_TOKEN_CHANGE_LIMIT.getParameterName());
		if (masterTokenChangeLimitParam == null || masterTokenChangeLimitParam.getIntegerValue() == null)
		{
			masterTokenChangeLimit = null;
		}
		else
		{
			masterTokenChangeLimit = masterTokenChangeLimitParam.getIntegerValue();
		}

		CSystemParameter authenticationTokenChangeLimitParam = systemParameterService.findByName(ECoreSystemParameter.AUTHENTICATION_TOKEN_CHANGE_LIMIT.getParameterName());
		if (authenticationTokenChangeLimitParam == null || authenticationTokenChangeLimitParam.getIntegerValue() == null)
		{
			authenticationTokenChangeLimit = null;
		}
		else
		{
			authenticationTokenChangeLimit = authenticationTokenChangeLimitParam.getIntegerValue();
		}
	}

	@Override
	public boolean isMasterTokenIpIgnored ()
	{
		return masterTokenIpIgnored;
	}

	@Override
	public boolean isAuthenticationTokenIpIgnored ()
	{
		return authenticationTokenIpIgnored;
	}

	@Override
	public Integer getMasterTokenExpireLimit ()
	{
		return masterTokenExpireLimit;
	}

	@Override
	public Integer getAuthenticationTokenExpireLimit ()
	{
		return authenticationTokenExpireLimit;
	}

	@Override
	public Integer getMasterTokenChangeLimit ()
	{
		return masterTokenChangeLimit;
	}

	@Override
	public Integer getAuthenticationTokenChangeLimit ()
	{
		return authenticationTokenChangeLimit;
	}
}
