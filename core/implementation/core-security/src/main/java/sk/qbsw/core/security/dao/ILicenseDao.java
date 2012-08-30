/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.security.model.domain.CLicense;

/**
 * The Interface ILicenseDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
public interface ILicenseDao extends Serializable
{

	/**
	 * Make/update persistent entity
	 * @param license entity
	 */
	public void persit(CLicense<?> license);
	
	/**
	 * Find by identifier.
	 *
	 * @param id entity identifier
	 * @return the entity
	 */
	public CLicense<?> findById (Long id);
	
	/**
	 * remove persistent object.
	 *
	 * @param license the license
	 */
	public void delete (CLicense<?> license);

	/**
	 * Find by organization id.
	 *
	 * @param orgId the org id
	 * @return the list
	 */
	public List<CLicense<?>> findByOrganizationId (Long orgId);
	

}
