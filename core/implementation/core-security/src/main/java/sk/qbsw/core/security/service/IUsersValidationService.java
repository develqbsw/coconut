package sk.qbsw.core.security.service;

import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUser;

public interface IUsersValidationService
{

	public abstract Boolean isOrganizationExists (COrganization organization);
	
	public abstract Boolean isUserExists (CUser user);
	
	public abstract Boolean leastOneAdmin (CUser user, COrganization organization, String group);
}
