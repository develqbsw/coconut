package sk.qbsw.core.security.service;

import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUser;

public interface IUsersValidationService
{

	public abstract Boolean isOrganizationExists (COrganization organization);

	public abstract Boolean isOrganizationExists (String name);

	public abstract Boolean isUserExists (CUser user);

	public abstract Boolean isUserExists (String login);

	public abstract Boolean isUserExistsPin (String pin);

	public abstract Boolean isUserExistsPin (CUser user);

	public abstract Boolean leastOneAdmin (CUser user, COrganization organization, String group);
}
