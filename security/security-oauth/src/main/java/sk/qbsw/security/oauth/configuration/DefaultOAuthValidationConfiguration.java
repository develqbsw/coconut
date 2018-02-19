package sk.qbsw.security.oauth.configuration;

import ESystemParameters.ECoreSystemParameter;
import sk.qbsw.core.configuration.model.domain.CSystemParameter;
import sk.qbsw.core.configuration.service.ISystemParameterService;

import javax.annotation.PostConstruct;

/**
 * The default oauth validation configuration.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class DefaultOAuthValidationConfiguration implements OAuthValidationConfiguration
{
	private final ISystemParameterService systemParameterService;

	/** The master token expire limit in hours. */
	private Integer masterTokenExpireLimit;

	/** The authentication token expire limit in hours. */
	private Integer authenticationTokenExpireLimit;

	/** The change limit in hours - ak je < 1, tak ignorovat, beriem to tak, ze je to vypnute lebo to niekto chcel vypnut. */
	private Integer masterTokenChangeLimit;

	/** The authentication token change limit. */
	private Integer authenticationTokenChangeLimit;

	public DefaultOAuthValidationConfiguration (ISystemParameterService systemParameterService)
	{
		this.systemParameterService = systemParameterService;
	}

	@PostConstruct
	public void initProperties ()
	{
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
