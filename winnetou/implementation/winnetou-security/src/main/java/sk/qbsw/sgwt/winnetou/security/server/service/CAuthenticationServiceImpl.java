package sk.qbsw.sgwt.winnetou.security.server.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.sgwt.winnetou.client.exception.CBusinessException;
import sk.qbsw.sgwt.winnetou.client.model.security.CLoggedUserRecord;
import sk.qbsw.sgwt.winnetou.security.client.model.ISecuritySessionAttributes;
import sk.qbsw.sgwt.winnetou.security.client.service.IAuthenticationService;
import sk.qbsw.sgwt.winnetou.security.client.ui.localization.CClientExceptionMessages;
import sk.qbsw.sgwt.winnetou.server.exception.CServletSessionUtils;

/**
 * Security management service
 * 
 * @author Dalibor Rak
 * @since 0.1
 * 
 */
@Service (value = "authenticateServiceImpl")
public class CAuthenticationServiceImpl implements IAuthenticationService
{
	@Autowired
	private sk.qbsw.core.security.service.IAuthenticationService authenticator;
	
	@Transactional
	public CLoggedUserRecord authenticate (String login, String password) throws CBusinessException
	{
		try
		{
			CRole roleLogin = new CRole();
			roleLogin.setCode("LOGIN");
			
			if (!authenticator.canLogin(login, password, roleLogin))
			{
				throw new CBusinessException(CClientExceptionMessages.AUTHORIZATION_INSUFFICIENT_RIGHTS);
			}

			// prepares object to store on the client side
			CLoggedUserRecord retVal = new CLoggedUserRecord();
			CUser user = authenticator.login(login, password);
			retVal.setLogin(user.getLogin());
			retVal.setName(user.getName());
			retVal.setSurname(user.getSurname());
			retVal.setUserId(user.getId());

			retVal.setRoles(user.exportRoles());
			
			Logger.getLogger(CAuthenticationServiceImpl.class).debug("CAuthenticationService session : " + CServletSessionUtils.getHttpSession().getId());

			CServletSessionUtils.getHttpSession().setAttribute(ISecuritySessionAttributes.ATTR_LOGGED_USER, retVal);
			return retVal;
		}
		catch(CSecurityException ex){
			throw new CBusinessException(CClientExceptionMessages.AUTHENTICATION_FAILED);
		}
//		catch (CUserInactiveException ex)
//		{
//			throw new CBusinessException(CClientExceptionMessages.AUTHENTICATION_USER_INACTIVE);
//		}
//		catch (CAuthenticationException ex)
//		{
//			throw new CBusinessException(CClientExceptionMessages.AUTHENTICATION_FAILED);
//		}
	}

	public CLoggedUserRecord getLoggedUserInfo ()
	{
		return (CLoggedUserRecord) CServletSessionUtils.getHttpSession().getAttribute(ISecuritySessionAttributes.ATTR_LOGGED_USER);
	}


	/**
	 * Removes security attributes from session
	 */
	public void logout ()
	{
		CServletSessionUtils.getHttpSession().setAttribute(ISecuritySessionAttributes.ATTR_LOGGED_USER, null);
	}
}
