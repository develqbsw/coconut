package sk.qbsw.security.organization.complex.core.dao;


import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.organization.complex.core.model.domain.CXOOrganization;

import java.util.List;

/**
 * The complex organization dao.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface CXOOrganizationDao extends IEntityDao<Long, CXOOrganization>
{
	/**
	 * Find by name list.
	 *
	 * @param name the name
	 * @return the list
	 */
	List<CXOOrganization> findByName (String name);

	/**
	 * Find one by code organization.
	 *
	 * @param code the code
	 * @return the organization
	 */
	CXOOrganization findOneByCode (String code);
}
