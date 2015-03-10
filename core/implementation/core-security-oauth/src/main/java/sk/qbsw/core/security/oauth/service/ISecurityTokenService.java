/**
 * 
 */
package sk.qbsw.core.security.oauth.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * @author podmajersky
 * @version 1.3.0
 * @since 1.0.0
 *
 */
public interface ISecurityTokenService {

	CAuthenticationParams findByUser(long userId);

	/**
	 * vygeneruje, ulozi novy token pre daneho usera a vrati tento token
	 * @param ip 
	 * @param userId
	 * @return
	 */
	String saveNewTokenForUser(CUser user, String ip);

	void changePassword(String login, String oldPassword, String newPassword, String pin) throws CSecurityException;

	/**
	 * najde informacie o tokene, overi jeho platnosti, upravy datum posledneho pristup a ak je platny, tak vrati pouzivatela naviazaneho na dany token
	 * 
	 * @param token
	 * @param ip 
	 * @return pouzivatela naviazaneho na dany token
	 */
	CUser findByToken(String token, String ip);

	void removeTokenForUser(long userId);
}
