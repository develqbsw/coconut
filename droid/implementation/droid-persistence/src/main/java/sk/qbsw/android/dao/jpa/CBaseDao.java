package sk.qbsw.android.dao.jpa;

import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sk.qbsw.android.dao.IBaseDao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

/**
 * Default parent for implementation of dao class 
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param <T> - type of database table
 * @param <D> - type of primary key
 */
public class CBaseDao<T, D> extends BaseDaoImpl<T, D> implements IBaseDao<T, D>
{

	public CBaseDao (Class<T> dataClass) throws SQLException
	{
		super(dataClass);
	}

	public CBaseDao (ConnectionSource connectionSource, Class<T> dataClass) throws SQLException
	{
		super(connectionSource, dataClass);
	}

	public CBaseDao (ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException
	{
		super(connectionSource, tableConfig);
	}

	/**
	 * get ids of records for specified select 
	 * @param select select to select ids
	 * @param params to select
	 * @return array of ids
	 * @throws SQLException when is error on database
	 */
	protected Long[] getIdsForSelect (String select, String... params) throws SQLException
	{
		List<Long> ids = new ArrayList<Long>();

		GenericRawResults<String[]> result = queryRaw(select, params);
		for (String[] strings : result)
		{
			ids.add(Long.parseLong(strings[0]));
		}

		return ids.toArray(new Long[0]);
	}

	@Override
	public ConnectionSource getConnectionSource ()
	{
		return this.connectionSource;
	}

	/**
	 * format date to standard SQLite database string format
	 * @param date - date to format
	 * @return formatted date
	 */
	public static String formatToDateDatabaseString (Date date)
	{
		Format dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.getDefault());
		return dateFormatter.format(date);
	}

}
