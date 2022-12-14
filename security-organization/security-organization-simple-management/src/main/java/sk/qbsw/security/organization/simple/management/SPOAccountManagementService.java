package sk.qbsw.security.organization.simple.management;

import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountDataTypes;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.management.service.AccountManagementService;

import java.util.List;

/**
 * The simple organization account management service.
 *
 * @param <I> the simple organization account input data type
 * @param <O> the simple organization account output type
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 2.2.0
 * @since 2.0.0
 */
public interface SPOAccountManagementService<I extends AccountInputData, O extends AccountData> extends AccountManagementService<I, O>
{
	/**
	 * Find one by organization code and login and state o.
	 *
	 * @param organizationCode the organization code
	 * @param login            the login
	 * @param state            the state
	 * @return the o
	 */
	O findOneByOrganizationCodeAndLoginAndState (String organizationCode, String login, ActivityStates state);

	/**
	 * Find by organization code list.
	 *
	 * @param organizationCode the organization code
	 * @return the list
	 */
	List<O> findByOrganizationCode (String organizationCode);

	/**
	 * Find by organization code and group code and not login list.
	 *
	 * @param organizationCode the organization code
	 * @param groupCode        the group code
	 * @param login            the login
	 * @return the list
	 */
	List<O> findByOrganizationCodeAndGroupCodeAndNotLogin (String organizationCode, String groupCode, String login);

	/**
	 * Find by organization code and state list.
	 *
	 * @param organizationCode the organization code
	 * @param state            the state
	 * @return the list
	 */
	List<O> findByOrganizationCodeAndState (String organizationCode, ActivityStates state);

	/**
	 * Find by organization code and state and group code list.
	 *
	 * @param organizationCode the organization code
	 * @param state            the state
	 * @param groupCode        the group code
	 * @return the list
	 */
	List<O> findByOrganizationCodeAndStateAndGroupCode (String organizationCode, ActivityStates state, String groupCode);

	/**
	 * Find by organization code and state and group code order by organization name and login list.
	 *
	 * @param organizationCode the organization code
	 * @param state            the state
	 * @param groupCode        the group code
	 * @return the list
	 */
	List<O> findByOrganizationCodeAndStateAndGroupCodeOrderByOrganizationNameAndLogin (String organizationCode, ActivityStates state, String groupCode);

	/**
	 * Find by organization code and role code list.
	 *
	 * @param organizationCode the organization code
	 * @param roleCode         the role code
	 * @return the list
	 */
	List<O> findByOrganizationCodeAndRoleCode (String organizationCode, String roleCode);

	/**
	 * Find by type and organization code list.
	 *
	 * @param type             the type
	 * @param organizationCode the organization code
	 * @return the list
	 */
	List<O> findByTypeAndOrganizationCode (AccountDataTypes type, String organizationCode);
}
