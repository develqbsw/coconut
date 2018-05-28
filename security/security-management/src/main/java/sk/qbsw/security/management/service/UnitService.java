package sk.qbsw.security.management.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Address;
import sk.qbsw.security.core.model.domain.Unit;

/**
 * The Interface UnitService.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.6.0
 */
public interface UnitService extends Serializable
{
	/**
	 * Gets the all units.
	 *
	 * @return the all units
	 */
	public List<Unit> getAll ();

	/**
	 * Gets the all units by user.
	 *
	 * @param user the user
	 * @return the all units
	 * @throws CSecurityException throws if the user is null
	 */
	public List<Unit> getAll (Account user) throws CSecurityException;


	/**
	 * Add or update unit address
	 * 
	 * @param unit unit for which is address updated
	 * @param address address which is added or updated for unit
	 */
	public void setAddress (Unit unit, Address address);
}
