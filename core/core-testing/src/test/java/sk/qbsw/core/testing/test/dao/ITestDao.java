package sk.qbsw.core.testing.test.dao;

import java.io.Serializable;
import java.util.List;

/**
 * The interface for test dao.
 *
 * @author Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
public interface ITestDao extends Serializable
{
	/**
	 * Finds all.
	 *
	 * @return list of integers
	 */
	public List<Integer> findAll ();

	/**
	 * Find one.
	 *
	 * @return the integer
	 */
	public int find ();
}
