package sk.qbsw.core.security.service;

import org.springframework.stereotype.Service;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CRole;

/**
 * The database authorization service.
 *
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@Service (value = "databaseAuthorizationService")
public class CDatabaseAuthorizationService implements IAuthorizationService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	public void checkAccessRights (String login, CRole role, String organizationUnit, String category) throws CSecurityException
	{
		// TODO Auto-generated method stub
	}
}
