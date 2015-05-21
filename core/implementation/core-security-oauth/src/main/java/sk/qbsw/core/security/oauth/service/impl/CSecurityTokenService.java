/**
 * 
 */
package sk.qbsw.core.security.oauth.service.impl;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.EPasswordType;
import sk.qbsw.core.security.oauth.dao.ISecurityTokenDao;
import sk.qbsw.core.security.oauth.model.CSecurityToken;
import sk.qbsw.core.security.oauth.service.ICheckTokenStrategy;
import sk.qbsw.core.security.oauth.service.IIdGeneratorService;
import sk.qbsw.core.security.oauth.service.ISecurityTokenService;
import sk.qbsw.core.security.service.IAuthenticationModifierService;
import sk.qbsw.core.security.service.IAuthenticationService;

/**
 * The Class CSecurityTokenService.
 *
 * @author podmajersky
 * @version 1.13.0
 * @since 1.13.0
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

	/** The authentication service. */
	@Qualifier("cLoginService")
	@Autowired
	private IAuthenticationModifierService authenticationModifierService;
	
	/** The check token strategy. */
	@Autowired
	private ICheckTokenStrategy checkTokenStrategy;
	
	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.ISecurityTokenService#findByUser(long)
	 */
	@Transactional(readOnly = true)
	@Override
	public CAuthenticationParams findByUser(long userId) {
		return paramsDao.findOneByUserId(userId);
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
		CAuthenticationParams params = paramsDao.findOneByUserId(user.getId());
		if (EPasswordType.FIRST_TIME == params.getPasswordType()) {
			if (!params.getPin().equals(pin)) {
				throw new CSecurityException("Nezhoduje pin s id: " + params.getId() + " s pinom s requestu: " + pin, ECoreErrorResponse.PIN_WRONG);
			}
			//nastavenie durable hesla
			params.setPasswordType(EPasswordType.DURABLE);
			paramsDao.update(params);
		}

		// zmena hesla
		authenticationModifierService.changeEncryptedPassword(login, newPassword);
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

		if (checkTokenStrategy.hasExpired(secToken)) {
			log.warn("token expiroval");
			removeTokenForUser(secToken.getUser().getId());
			return null;
		}

		if (checkTokenStrategy.hasToBeChangedDueInactivity(secToken)) {
			log.warn("token je nutne zmenit");
			removeTokenForUser(secToken.getUser().getId());
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
