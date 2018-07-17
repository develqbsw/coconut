package sk.qbsw.organization.complex.core.dao;


import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.organization.complex.core.model.domain.Organization;

import java.util.List;

/**
 * The organization dao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.0.0
 */
public interface OrganizationDao extends IEntityDao<Long, Organization>
{
	/**
	 * Find by name list.
	 *
	 * @param name the name
	 * @return the list
	 */
	List<Organization> findByName (String name);
	
	/**
	 * Find one by code organization.
	 *
	 * @param code the code
	 * @return the organization
	 */
	Organization findOneByCode (String code);
}
