package sk.qbsw.core.security.service;

import java.util.List;

import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;



public interface IUserService
{

	public abstract List<CUser> getUsers ();

	public abstract List<CUser> getUsers (COrganization organization, CRole role);

	public abstract List<CUser> getUsers (COrganization organization, Boolean enabled, CGroup group);

	public abstract CUser getUserByLogin (String login);

	public abstract CUser getUserByPin (String pin);

	public abstract void updateUser (CUser user);

	/**
	 * Find by login  and return NULL if user not exist - NOT exeption.
	 *
	 * @param login the login
	 * @return the c user or null if user not exist
	 */
	public abstract CUser getUserByLoginNull (String login);

	public abstract CUser get (Long id);

	/**
	 * Register new user
	 * 
	 * @param model
	 */
	public void registerNewUser (CUser user, COrganization organization);

	/** Get users without user what come as parameter
	 * @param organization - organization for which are selected users
	 * @param group - group for which are selected users
	 * @param user - user without are users returned
	 * @return list of users
	 */
	public abstract List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user);


}
