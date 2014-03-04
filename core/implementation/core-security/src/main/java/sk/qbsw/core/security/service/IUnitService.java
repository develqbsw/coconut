package sk.qbsw.core.security.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface IUnitService.
 * 
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
public interface IUnitService extends Serializable
{
	/**
	 * Gets the all units.
	 *
	 * @return the all units
	 */
	public abstract List<CUnit> getAll ();

	/**
	 * Gets all units by user
	 * 
	 * @param user
	 * @return
	 */
	List<CUnit> getAllByUser (CUser user);
}
