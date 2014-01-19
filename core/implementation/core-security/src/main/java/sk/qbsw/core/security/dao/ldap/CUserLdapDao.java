package sk.qbsw.core.security.dao.ldap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.apache.directory.api.ldap.model.entry.Attribute;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapInvalidAttributeValueException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.persistence.dao.jpa.AEntityLdapDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;
import sk.qbsw.core.security.service.ldap.CLdapProvider;

/**
 * User LDAP DAO implementation.
 *
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@Repository (value = "userLdapDao")
public class CUserLdapDao extends AEntityLdapDao<Long, CUser> implements IUserDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ldap configurator. */
	@Autowired
	private ILdapAuthenticationConfigurator ldapConfigurator;

	/** The ldap provider. */
	@Autowired
	private CLdapProvider ldapProvider;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByLogin(java.lang.String)
	 */
	@Override
	public CUser findByLogin (String login)
	{
		try
		{
			ldapProvider.createConnection(ldapConfigurator.getServerName(), ldapConfigurator.getServerPort());
			ldapProvider.bindOnServer(ldapConfigurator.getUserDn(), ldapConfigurator.getUserPassword());

			Entry userEntry = ldapProvider.searchSingleResult(ldapConfigurator.getUserSearchBaseDn(), "(&(uid=" + login + "))", SearchScope.SUBTREE, "*");
			Set<Entry> userGroupEntries = ldapProvider.searchResults(ldapConfigurator.getGroupSearchBaseDn(), "(&(uniqueMember=" + userEntry.getDn().toString() + "))", SearchScope.SUBTREE, "*");

			return createLdapUser(userEntry, userGroupEntries);

		}
		catch (CBusinessException ex)
		{
			throw new CSystemException("The user with login " + login + " is not in ldap or not unique.");
		}
		catch (LdapInvalidAttributeValueException ex)
		{
			throw new CSystemException("The ldap error occured: " + ex.getMessage());
		}
		finally
		{
			ldapProvider.unbindFromServer();
			ldapProvider.closeConnection();
		}
	}


	/**
	 * Creates the user groups from ldap entries.
	 *
	 * @param userGroupEntries the user group entries
	 * @return the sets of groups
	 * @throws LdapInvalidAttributeValueException the ldap invalid attribute value exception
	 */
	private Set<CGroup> createUserGroups (Set<Entry> userGroupEntries) throws LdapInvalidAttributeValueException
	{
		Set<CGroup> groups = new HashSet<CGroup>();

		for (Entry groupEntry : userGroupEntries)
		{
			CGroup group = new CGroup();
			group.setCode(groupEntry.get("cn").getString());

			groups.add(group);
		}

		return groups;
	}


	/**
	 * Gets the user's unit.
	 *
	 * @param unitName the name of unit
	 * @return the unit
	 */
	private CUnit getUserDefaultUnit (String unitName)
	{
		CUnit unit = new CUnit();
		unit.setName(unitName);

		return unit;
	}

	/**
	 * Creates the LDAP user - check mandatory attribute and fill it.
	 *
	 * @param userEntry the user entry
	 * @param userGroupEntries the user group entries
	 * @return the user
	 * @throws LdapInvalidAttributeValueException the invalid LDAP attribute value exception
	 */
	private CUser createLdapUser (Entry userEntry, Set<Entry> userGroupEntries) throws LdapInvalidAttributeValueException
	{
		CUser user = new CUser();

		Attribute givenName = userEntry.get("givenName");
		Attribute surname = userEntry.get("sn");
		Attribute login = userEntry.get("uid");
		Attribute organizationUnit = userEntry.get("ou");

		if (surname == null || login == null)
		{
			throw new CSystemException("The mandatory LDAP attribute 'sn' or 'uid' is missing.");
		}

		user.setLogin(login.getString());
		user.setSurname(surname.getString());

		if (givenName != null)
		{
			user.setName(givenName.getString());
		}
		if (organizationUnit != null)
		{
			user.setDefaultUnit(getUserDefaultUnit(organizationUnit.getString()));
		}

		//set user groups
		user.setGroups(createUserGroups(userGroupEntries));

		return user;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByLogin(java.lang.String, sk.qbsw.core.security.model.domain.CUnit)
	 */
	@Override
	public CUser findByLogin (String login, CUnit unit)
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#save(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void save (CUser object)
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#remove(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void remove (CUser object)
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findAll()
	 */
	@Override
	public List<CUser> findAll ()
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.lang.Object)
	 */
	@Override
	public CUser findById (Long id)
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.util.List)
	 */
	@Override
	public List<CUser> findById (List<Long> ids)
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#flush()
	 */
	@Override
	public void flush ()
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findForModification(java.lang.Long)
	 */
	@Override
	public CUser findForModification (Long pkId)
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByPinNull(java.lang.String)
	 */
	@Override
	public CUser findByPinNull (String pinCode)
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
	public List<CUser> findAllUsers (COrganization organization)
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Override
	public List<CUser> findAllUsers (COrganization organization, Boolean enabled, CGroup group)
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers()
	 */
	@Override
	public List<CUser> findAllUsers ()
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#getOtherActiveUsers(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CGroup, sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
	public List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user)
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsersByRole(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
	public List<CUser> findAllUsersByRole (COrganization organization, CRole role)
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsersOrderByOrganization(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Override
	public List<CUser> findAllUsersOrderByOrganization (COrganization organization, Boolean enabled, CGroup group)
	{
		throw new NotImplementedException();
	}
}
