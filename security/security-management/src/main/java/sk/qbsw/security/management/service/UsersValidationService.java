package sk.qbsw.security.management.service;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Organization;

public interface UsersValidationService
{

	public abstract Boolean isOrganizationExists (Organization organization);

	public abstract Boolean isOrganizationExists (String name);

	public abstract Boolean isUserExists (Account user);

	public abstract Boolean isUserExists (String login);

	public abstract Boolean isUserExistsPin (String pin);

	public abstract Boolean isUserExistsPin (Account user);

	public abstract Boolean leastOneAdmin (Account user, Organization organization, String group);
}
