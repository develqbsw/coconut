package sk.qbsw.security.oauth.test.configuration;

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

	@PostConstruct
	public void initProperties ()
	{
		masterTokenIpIgnored = false;
		authenticationTokenIpIgnored = false;
		masterTokenExpireLimit = 1;
		masterTokenChangeLimit = 3;
		authenticationTokenExpireLimit = 1;
		authenticationTokenChangeLimit = 3;
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
