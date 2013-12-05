package sk.qbsw.android.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Interface to parent of all dao objects
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param <T> - type of database table
 * @param <D> - type of primary key
 */
public interface IBaseDao<T, D> extends Dao<T, D>
{
	/**
	 * get connection source for dao - can be used in transactions
	 * @return connection source
	 */
	public abstract ConnectionSource getConnectionSource (); 
}
