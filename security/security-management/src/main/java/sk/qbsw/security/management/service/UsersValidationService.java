package sk.qbsw.security.management.service;

import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.User;

public interface UsersValidationService
{

	public abstract Boolean isOrganizationExists (Organization organization);

	public abstract Boolean isOrganizationExists (String name);

	public abstract Boolean isUserExists (User user);

	public abstract Boolean isUserExists (String login);

	public abstract Boolean isUserExistsPin (String pin);

	public abstract Boolean isUserExistsPin (User user);

	public abstract Boolean leastOneAdmin (User user, Organization organization, String group);
}
