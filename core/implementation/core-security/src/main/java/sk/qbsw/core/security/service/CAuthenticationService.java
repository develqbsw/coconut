package sk.qbsw.core.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.exception.CUserDisabledException;
import sk.qbsw.core.security.exception.CWrongPasswordException;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

@Service (value = "cLoginService")
public class CAuthenticationService implements IAuthenticationService
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IUserDao userDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.winnetou.security.service.IAuthenticationService#canLogin(java.lang.String, java.lang.String)
	 */
	@Transactional (readOnly = true)
	public CUser login (String login, String password) throws CSecurityException
	{
		CUser user = userDao.findForLogin(login, password);
		if (user == null)
		{
			throw new CWrongPasswordException("");
		}
		else if (user.getFlagEnabled() == false)
		{
			throw new CUserDisabledException("");
		}
		
		return user;
	}

	//	@Transactional (readOnly = true)
	//	public CUser login (String login, String password)
	//	{
	//		return userDao.findForLogin(login, password);
	//	}

	@Transactional (readOnly = true)
	public CUser login (String login, String password, CRole role)
	{
		return userDao.findByLoginAndRole(login, password, role);
	}

	@Transactional (readOnly = true)
	public boolean canLogin (String login, String password, CRole role)
	{
		return userDao.findByLoginAndRole(login, password, role) != null;
	}
}
