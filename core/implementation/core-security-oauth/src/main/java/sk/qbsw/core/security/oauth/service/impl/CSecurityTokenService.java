/**
 * 
 */
package sk.qbsw.core.security.oauth.service.impl;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ESystemParameters.ECoreSystemParameter;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.configuration.model.domain.CSystemParameter;
import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.EPasswordType;
import sk.qbsw.core.security.oauth.dao.ISecurityTokenDao;
import sk.qbsw.core.security.oauth.model.CSecurityToken;
import sk.qbsw.core.security.oauth.service.IIdGeneratorService;
import sk.qbsw.core.security.oauth.service.ISecurityTokenService;
import sk.qbsw.core.security.service.IAuthenticationService;

/**
 * The Class CSecurityTokenService.
 *
 * @author podmajersky
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class CSecurityTokenService implements ISecurityTokenService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** The token dao. */
	@Autowired
	private ISecurityTokenDao tokenDao;

	/** The params dao. */
	@Autowired
	private IAuthenticationParamsDao paramsDao;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The id generator service. */
	@Autowired
	private IIdGeneratorService idGeneratorService;

	/** The authentication service. */
	@Qualifier("cLoginService")
	@Autowired
	private IAuthenticationService authenticationService;

	/** The system parameter service. */
	@Autowired
	private ISystemParameterService systemParameterService;

	/** The expire limit. */
	private Integer expireLimit;

	/** ak je < 1, tak ignorovat, beriem to tak, ze je to vypnute lebo to niekto chcel vypnut. */
	private Integer changeLimit;

	/**
	 * Inits the properties.
	 */
	@PostConstruct
	public void initProperties() {
		CSystemParameter expireLimitParam = systemParameterService.findByName(ECoreSystemParameter.TOKEN_EXPIRE_LIMIT.getParameterName());
		if (expireLimitParam == null || expireLimitParam.getIntegerValue() == null || expireLimitParam.getIntegerValue() < 1) {
			throw new IllegalStateException("nie je nastaveny casovy limit expiracie tokenu");
		}
		expireLimit = expireLimitParam.getIntegerValue();

		CSystemParameter changeLimitParam = systemParameterService.findByName(ECoreSystemParameter.TOKEN_CHANGE_LIMIT.getParameterName());
		if (changeLimitParam == null || changeLimitParam.getIntegerValue() == null) {
			throw new IllegalStateException("nie je nastaveny casovy limit pre nutnost zmeny tokenu");
		}
		changeLimit = changeLimitParam.getIntegerValue();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.ISecurityTokenService#findByUser(long)
	 */
	@Transactional(readOnly = true)
	@Override
	public CAuthenticationParams findByUser(long userId) {
		return paramsDao.findByUserId(userId);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.ISecurityTokenService#saveNewTokenForUser(sk.qbsw.core.security.model.domain.CUser, java.lang.String)
	 */
	@Transactional
	@Override
	public String saveNewTokenForUser(CUser user, String ip) {
		CSecurityToken token = tokenDao.findByUserId(user.getId());
		if (token == null) {
			token = new CSecurityToken();
			token.setUser(user);
		}
		token.setToken(idGeneratorService.getGeneratedId());
		token.setCreateDate(new DateTime());
		token.setIp(ip);
		tokenDao.update(token);

		return token.getToken();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.ISecurityTokenService#changePassword(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(readOnly = false)
	@Override
	public void changePassword(String login, String oldPassword, String newPassword, String pin) throws CSecurityException {
		// overenie loginu a hesla
		CUser user = authenticationService.login(login, oldPassword);
		// ak je prvy login, overenie pinu
		CAuthenticationParams params = paramsDao.findByUserId(user.getId());
		if (EPasswordType.FIRST_TIME == params.getPasswordType()) {
			if (!params.getPin().equals(pin)) {
				throw new CSecurityException("Nezhoduje pin s id: " + params.getId() + " s pinom s requestu: " + pin, ECoreErrorResponse.PIN_WRONG);
			}
			//nastavenie durable hesla
			params.setPasswordType(EPasswordType.DURABLE);
			paramsDao.update(params);
		}

		// zmena hesla
		authenticationService.changeEncryptedPassword(login, newPassword);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.ISecurityTokenService#findByToken(java.lang.String, java.lang.String)
	 */
	@Transactional(readOnly = false)
	@Override
	public CUser findByToken(String token, String ip) {
		CSecurityToken secToken = tokenDao.findByToken(token, ip);
		if (secToken == null) {
			return null;
		}
		DateTime expDate = secToken.getLastAccessDate().plusMinutes(expireLimit);
		if (expDate.isBeforeNow()) {
			removeTokenForUser(secToken.getUser().getId());
			log.warn("token expiroval v case {}", expDate);
			return null;
		}
		DateTime changeDate = secToken.getCreateDate().plusHours(changeLimit);
		if (changeLimit > 0 && changeDate.isBeforeNow()) {
			removeTokenForUser(secToken.getUser().getId());
			log.warn("token bolo nutne zmenit do casu {}", expDate);
			return null;
		}
		// kvoli zmene last access date
		secToken.updateLastAccess();
		tokenDao.update(secToken);

		return secToken.getUser();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.ISecurityTokenService#removeTokenForUser(long)
	 */
	@Transactional(readOnly = false)
	@Override
	public void removeTokenForUser(long userId) {
		CSecurityToken token = tokenDao.findByUserId(userId);
		token.setToken(null);

		tokenDao.update(token);
	}

}
