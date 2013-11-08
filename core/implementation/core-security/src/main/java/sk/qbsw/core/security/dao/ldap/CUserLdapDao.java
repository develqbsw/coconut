package sk.qbsw.core.security.dao.ldap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidAttributeValueException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.persistence.dao.jpa.AEntityLdapDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * User LDAP DAO implementation.
 *
 * @author Tomas Lauro
 * @version 1.7.0
 * @since 1.7.0
 */
@Repository (value = "userLdapDao")
public class CUserLdapDao extends AEntityLdapDao<Long, CUser> implements IUserDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ldap server name. */
	@Value ("${ldap.server_name}")
	private String ldapServerName;

	/** The ldap server port. */
	@Value ("${ldap.server_port}")
	private int ldapServerPort;

	/** The ldap dn of an user to authenticate with ldap server. */
	@Value ("${ldap.user_dn}")
	private String ldapUserDn;

	/** The ldap user password. */
	@Value ("${ldap.user_password}")
	private String ldapUserPassword;

	/** The ldap user search base dn. */
	@Value ("${ldap.user_search_base_dn}")
	private String ldapUserSearchBaseDn;

	/** The ldap group search base dn. */
	@Value ("${ldap.group_search_base_dn}")
	private String ldapGroupSearchBaseDn;

	/** The ldap organization search base dn. */
	@Value ("${ldap.organization_search_base_dn}")
	private String ldapOrganizationSearchBaseDn;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByLogin(java.lang.String)
	 */
	@Override
	public CUser findByLogin (String login)
	{
		LdapConnection connection = createConnection(ldapServerName, ldapServerPort);
		bindOnServer(connection, ldapUserDn, ldapUserPassword);

		try
		{
			EntryCursor cursor = connection.search(ldapUserSearchBaseDn, "(&(uid=" + login + "))", SearchScope.SUBTREE, "*");

			if (cursor.next() == true)
			{
				Entry userEntry = cursor.get();

				if (cursor.next() == true)
				{
					throw new CSystemException("The user with login " + login + " is not unique.");
				}

				CUser user = new CUser();
				user.setName(userEntry.get("givenName").getString());
				user.setSurname(userEntry.get("sn").getString());
				user.setLogin(userEntry.get("uid").getString());
				user.setGroups(getUserGroups(connection, userEntry.getDn()));
				user.setOrganization(getUserOrganization(connection));

				return user;
			}
			else
			{
				return null;
			}
		}
		catch (LdapInvalidAttributeValueException ex)
		{
			throw new CSystemException("An LdapInvalidAttributeValueException exception raised: " + ex.toString());
		}
		catch (LdapException ex)
		{
			throw new CSystemException("An LdapException exception raised: " + ex.toString());
		}
		catch (CursorException ex)
		{
			throw new CSystemException("An CursorException exception raised: " + ex.toString());
		}
		finally
		{
			unbindFromServer(connection);
			closeConnection(connection);
		}
	}

	/**
	 * Gets the user groups from LDAP server.
	 *
	 * @param connection the connection
	 * @param userDn the user DN
	 * @return the groups of LDAP user identified by DN
	 */
	private Set<CGroup> getUserGroups (LdapConnection connection, Dn userDn)
	{
		EntryCursor cursor = null;
		Set<CGroup> groups = new HashSet<CGroup>();

		try
		{
			cursor = connection.search(ldapGroupSearchBaseDn, "(&(uniqueMember=" + userDn.toString() + "))", SearchScope.SUBTREE, "*");

			while (cursor.next() == true)
			{
				Entry groupEntry = cursor.get();

				CGroup group = new CGroup();
				group.setCode(groupEntry.get("cn").getString());

				groups.add(group);
			}
		}
		catch (CursorException ex)
		{
			throw new CSystemException("An CursorException exception raised: " + ex.toString());
		}
		catch (LdapException ex)
		{
			throw new CSystemException("An LdapException exception raised: " + ex.toString());
		}
		finally
		{
			if (cursor != null)
			{
				cursor.close();
			}
		}

		return groups;
	}

	/**
	 * Gets the user organization from LDAP server.
	 *
	 * @param connection the connection
	 * @return the user organization
	 */
	private COrganization getUserOrganization (LdapConnection connection)
	{
		EntryCursor cursor = null;
		COrganization organization = null;

		try
		{
			cursor = connection.search(ldapOrganizationSearchBaseDn, "(objectClass=organization)", SearchScope.OBJECT, "*");

			if (cursor.next() == true)
			{
				Entry organizationEntry = cursor.get();

				organization = new COrganization();
				organization.setName(organizationEntry.get("o").getString());
			}
		}
		catch (CursorException ex)
		{
			throw new CSystemException("An CursorException exception raised: " + ex.toString());
		}
		catch (LdapException ex)
		{
			throw new CSystemException("An LdapException exception raised: " + ex.toString());
		}
		finally
		{
			if (cursor != null)
			{
				cursor.close();
			}
		}

		return organization;
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
}
