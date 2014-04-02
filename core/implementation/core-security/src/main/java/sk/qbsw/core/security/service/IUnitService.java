package sk.qbsw.core.security.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.security.model.domain.CAddress;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface IUnitService.
 * 
 * @author Tomas Lauro
 * @version 1.7.1
 * @since 1.6.0
 */
public interface IUnitService extends Serializable
{
	/**
	 * Gets the all units.
	 *
	 * @return the all units
	 */
	public List<CUnit> getAll ();

	/**
	 * Gets the all units by user.
	 *
	 * @param user the user
	 * @return the all units
	 */
	public List<CUnit> getAll (CUser user);
	
	
	/**
	 * Add or update unit address
	 * 
	 * @param unit unit for which is address updated
	 * @param address address which is added or updated for unit
	 */
	public void setAddress(CUnit unit, CAddress address);
}
