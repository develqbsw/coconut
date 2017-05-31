package sk.qbsw.security.management.service;

import sk.qbsw.security.core.model.domain.COrganization;
import sk.qbsw.security.core.model.domain.CUser;

public interface UsersValidationService
{

	public abstract Boolean isOrganizationExists (COrganization organization);

	public abstract Boolean isOrganizationExists (String name);

	public abstract Boolean isUserExists (CUser user);

	public abstract Boolean isUserExists (String login);

	public abstract Boolean isUserExistsPin (String pin);

	public abstract Boolean isUserExistsPin (CUser user);

	public abstract Boolean leastOneAdmin (CUser user, COrganization organization, String group);
}
