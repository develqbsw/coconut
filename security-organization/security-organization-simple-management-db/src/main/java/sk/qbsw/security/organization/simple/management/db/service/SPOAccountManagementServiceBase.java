package sk.qbsw.security.organization.simple.management.db.service;

import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.filter.AccountAssociationsFilter;
import sk.qbsw.security.core.model.filter.AccountDetailFilter;
import sk.qbsw.security.core.model.order.*;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.management.base.service.AccountManagementServiceBase;
import sk.qbsw.security.management.service.AccountCredentialManagementService;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The simple organization account management service.
 *
 * @param <I> the account input type
 * @param <O> the account output type
 * @param <A> the account type
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class SPOAccountManagementServiceBase<I extends AccountInputData, O extends AccountData, A extends Account>extends AccountManagementServiceBase<I, O, A>
{
	/**
	 * Instantiates a new Simple organization account management service base.
	 *
	 * @param accountDao the account dao
	 * @param organizationDao the organization dao
	 * @param authenticationParamsDao the authentication params dao
	 * @param authenticationService the authentication service
	 * @param accountAccountInputDataMapper the account account input data mapper
	 * @param accountOutputDataMapper the account output data mapper
	 */
	public SPOAccountManagementServiceBase (AccountDao<A> accountDao, UserDao userDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<I, A> accountAccountInputDataMapper, AccountOutputDataMapper<O, A> accountOutputDataMapper)
	{
		super(accountDao, userDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper);
	}

	/**
	 * Find one by organization code and login and state base o.
	 *
	 * @param organizationCode the organization code
	 * @param login the login
	 * @param state the state
	 * @return the o
	 */
	protected O findOneByOrganizationCodeAndLoginAndStateBase (String organizationCode, String login, ActivityStates state)
	{
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setLogin(login);
		filter.setState(state);
		filter.setOrganizationCode(organizationCode);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountDetailFilter(filter, orderModel).stream().map(accountOutputDataMapper::mapToAccountOutputData).findFirst().orElseThrow(NoResultException::new);
	}

	/**
	 * Find by organization code base list.
	 *
	 * @param organizationCode the organization code
	 * @return the list
	 */
	protected List<O> findByOrganizationCodeBase (String organizationCode)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganizationCode(organizationCode);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountAssociationsFilter(filter, orderModel).stream().map(accountOutputDataMapper::mapToAccountOutputData).collect(Collectors.toList());
	}

	/**
	 * Find by organization code and group code and not login base list.
	 *
	 * @param organizationCode the organization code
	 * @param groupCode the group code
	 * @param login the login
	 * @return the list
	 */
	protected List<O> findByOrganizationCodeAndGroupCodeAndNotLoginBase (String organizationCode, String groupCode, String login)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganizationCode(organizationCode);
		filter.setGroupCode(groupCode);
		filter.setExcludedLogin(login);
		filter.setState(ActivityStates.ACTIVE);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountAssociationsFilter(filter, orderModel).stream().map(accountOutputDataMapper::mapToAccountOutputData).collect(Collectors.toList());
	}

	/**
	 * Find by organization code and state base list.
	 *
	 * @param organizationCode the organization code
	 * @param state the state
	 * @return the list
	 */
	protected List<O> findByOrganizationCodeAndStateBase (String organizationCode, ActivityStates state)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganizationCode(organizationCode);
		filter.setState(state);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountAssociationsFilter(filter, orderModel).stream().map(accountOutputDataMapper::mapToAccountOutputData).collect(Collectors.toList());
	}

	/**
	 * Find by organization code and state and group code base list.
	 *
	 * @param organizationCode the organization code
	 * @param state the state
	 * @param groupCode the group code
	 * @return the list
	 */
	protected List<O> findByOrganizationCodeAndStateAndGroupCodeBase (String organizationCode, ActivityStates state, String groupCode)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganizationCode(organizationCode);
		filter.setState(state);
		filter.setGroupCode(groupCode);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountAssociationsFilter(filter, orderModel).stream().map(accountOutputDataMapper::mapToAccountOutputData).collect(Collectors.toList());
	}

	/**
	 * Find by organization code and state and group code order by organization name and login base list.
	 *
	 * @param organizationCode the organization code
	 * @param state the state
	 * @param groupCode the group code
	 * @return the list
	 */
	protected List<O> findByOrganizationCodeAndStateAndGroupCodeOrderByOrganizationNameAndLoginBase (String organizationCode, ActivityStates state, String groupCode)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganizationCode(organizationCode);
		filter.setState(state);
		filter.setGroupCode(groupCode);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(OrganizationOrderByAttributeSpecifiers.NAME, OrderSpecifiers.ASC));
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountAssociationsFilter(filter, orderModel).stream().map(accountOutputDataMapper::mapToAccountOutputData).collect(Collectors.toList());
	}

	/**
	 * Find by organization code and role code base list.
	 *
	 * @param organizationCode the organization code
	 * @param roleCode the role code
	 * @return the list
	 */
	protected List<O> findByOrganizationCodeAndRoleCodeBase (String organizationCode, String roleCode)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganizationCode(organizationCode);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		if (roleCode != null)
		{
			filter.setRoleCode(roleCode);
		}

		return accountDao.findByAccountAssociationsFilter(filter, orderModel).stream().map(accountOutputDataMapper::mapToAccountOutputData).collect(Collectors.toList());
	}
}
