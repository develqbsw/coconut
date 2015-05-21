package sk.qbsw.core.security.oauth.service.impl;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.qbsw.core.configuration.model.domain.CSystemParameter;
import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.core.security.oauth.model.CSecurityToken;
import sk.qbsw.core.security.oauth.service.ICheckTokenStrategy;
import ESystemParameters.ECoreSystemParameter;

/**
 * The check token expiration's limits strategy.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
@Component
public class CDefaultCheckTokenStrategy implements ICheckTokenStrategy
{
	/** The log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CDefaultCheckTokenStrategy.class);

	/** The system parameter service. */
	@Autowired
	private ISystemParameterService systemParameterService;

	/** The expire limit in hours. */
	private Integer expireLimit;

	/** The change limit in hours - ak je < 1, tak ignorovat, beriem to tak, ze je to vypnute lebo to niekto chcel vypnut. */
	private Integer changeLimit;

	/**
	 * Inits the properties from DB.
	 */
	@PostConstruct
	public void initProperties ()
	{
		CSystemParameter expireLimitParam = systemParameterService.findByName(ECoreSystemParameter.TOKEN_EXPIRE_LIMIT.getParameterName());
		if (expireLimitParam == null || expireLimitParam.getIntegerValue() == null || expireLimitParam.getIntegerValue() < 1)
		{
			throw new IllegalStateException("nie je nastaveny casovy limit expiracie tokenu");
		}
		expireLimit = expireLimitParam.getIntegerValue();

		CSystemParameter changeLimitParam = systemParameterService.findByName(ECoreSystemParameter.TOKEN_CHANGE_LIMIT.getParameterName());
		if (changeLimitParam == null || changeLimitParam.getIntegerValue() == null)
		{
			throw new IllegalStateException("nie je nastaveny casovy limit pre nutnost zmeny tokenu");
		}
		changeLimit = changeLimitParam.getIntegerValue();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.ICheckTokenStrategy#hasExpired(sk.qbsw.core.security.oauth.model.CSecurityToken)
	 */
	@Override
	public boolean hasExpired (CSecurityToken secToken)
	{
		DateTime expDate = secToken.getLastAccessDate().plusMinutes(expireLimit);
		if (expDate.isBeforeNow())
		{
			LOGGER.warn("The token expiration time: {}", expDate);
			return true;
		}
		else
		{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.ICheckTokenStrategy#hasToBeChangedDueInactivity(sk.qbsw.core.security.oauth.model.CSecurityToken)
	 */
	@Override
	public boolean hasToBeChangedDueInactivity (CSecurityToken secToken)
	{
		DateTime changeDate = secToken.getCreateDate().plusHours(changeLimit);
		if (changeLimit > 0 && changeDate.isBeforeNow())
		{
			LOGGER.warn("The token inactivity expiration time: {}", changeDate);
			return true;
		}
		else
		{
			return false;
		}
	}
}
