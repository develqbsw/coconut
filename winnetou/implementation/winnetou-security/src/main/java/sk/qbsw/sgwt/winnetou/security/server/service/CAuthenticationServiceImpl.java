package sk.qbsw.sgwt.winnetou.security.server.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.IRoles;
import sk.qbsw.core.security.authentication.ILoginPasswordAuthentication;
import sk.qbsw.core.security.authorization.IAuthorization;
import sk.qbsw.core.security.dao.IRoleDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CAuthenticationException;
import sk.qbsw.core.security.exception.CUserInactiveException;
import sk.qbsw.core.security.model.CRole;
import sk.qbsw.core.security.model.CUser;
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
	private ILoginPasswordAuthentication authenticator;

	@Autowired
	private IAuthorization authorizator;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IRoleDao roleDao;

	@Transactional
	public CLoggedUserRecord authenticate (String login, String password) throws CBusinessException
	{
		try
		{
			authenticator.authenticate(login, password);
			if (!authorizator.containsRole(login, IRoles.R_LOGIN))
			{
				throw new CBusinessException(CClientExceptionMessages.AUTHORIZATION_INSUFFICIENT_RIGHTS);
			}

			// prepares object to store on the client side
			CLoggedUserRecord retVal = new CLoggedUserRecord();
			CUser user = userDao.findByLogin(login);
			retVal.setLogin(user.getLogin());
			retVal.setName(user.getName());
			retVal.setSurname(user.getSurname());
			retVal.setUserId(user.getId());

			List<CRole> roles = roleDao.findAllValid(login);
			for (CRole cRole : roles)
			{
				retVal.addRole(cRole.getName());
			}

			Logger.getLogger(CAuthenticationServiceImpl.class).debug("CAuthenticationService session : " + CServletSessionUtils.getHttpSession().getId());

			CServletSessionUtils.getHttpSession().setAttribute(ISecuritySessionAttributes.ATTR_LOGGED_USER, retVal);
			return retVal;
		}
		catch (CUserInactiveException ex)
		{
			throw new CBusinessException(CClientExceptionMessages.AUTHENTICATION_USER_INACTIVE);
		}
		catch (CAuthenticationException ex)
		{
			throw new CBusinessException(CClientExceptionMessages.AUTHENTICATION_FAILED);
		}
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
