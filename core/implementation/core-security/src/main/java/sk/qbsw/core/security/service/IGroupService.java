package sk.qbsw.core.security.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CUnit;

/**
 * Service for groups management.
 *
 * @author Michal Lacko
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.0.0
 */
public interface IGroupService extends Serializable
{
	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	public abstract List<CGroup> getAll ();

	/**
	 * Gets the by code.
	 *
	 * @param code the code
	 * @return the by code
	 */
	public abstract List<CGroup> getByCode (String code);

	/**
	 * Gets the by unit.
	 *
	 * @param unit the unit
	 * @return the by unit
	 */
	public abstract List<CGroup> getByUnit (CUnit unit);
}
