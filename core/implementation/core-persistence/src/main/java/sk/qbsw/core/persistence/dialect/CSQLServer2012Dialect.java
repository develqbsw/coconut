package sk.qbsw.core.persistence.dialect;

import org.hibernate.dialect.SQLServer2012Dialect;

/**
 * MS SQL server 2012 hibernate dialekt pre hibernate 3
 * 
 * podporuje sekvencie ktore poskytuje najnovsi MS SQL server.
 *
 * @author martinkovic
 * @author Tomas Lauro
 * 
 * @version 1.10.0
 * @since 1.6.0
 * 
 * @deprecated use {@link org.hibernate.dialect.SQLServer2012Dialect}
 */
@Deprecated
public class CSQLServer2012Dialect extends SQLServer2012Dialect
{
	//	private static final String DISTINCT = "distinct";
	//	private static final String SELECT = "select";
	//	private static final String FROM = " from ";
	//
	//	/* (non-Javadoc)
	//	 * @see org.hibernate.dialect.Dialect#getQuerySequencesString()
	//	 */
	//	@Override
	//	public String getQuerySequencesString ()
	//	{
	//		return "select name from sys.sequences";
	//	}
	//
	//	@Override
	//	public String getLimitString (String querySqlString, boolean hasOffset)
	//	{
	//		StringBuilder sb = new StringBuilder(querySqlString.trim().toLowerCase());
	//
	//		int orderByIndex = sb.indexOf("order by");
	//		CharSequence orderby = orderByIndex > 0 ? sb.subSequence(orderByIndex, sb.length()) : "ORDER BY CURRENT_TIMESTAMP";
	//
	//		// Delete the order by clause at the end of the query
	//		if (orderByIndex > 0)
	//		{
	//			sb.delete(orderByIndex, orderByIndex + orderby.length());
	//		}
	//
	//		// HHH-5715 bug fix
	//		replaceDistinctWithGroupBy(sb);
	//
	//		insertRowNumberFunction(sb, orderby);
	//
	//		// Wrap the query within a with statement:
	//		sb.insert(0, "WITH query AS (").append(") SELECT * FROM query ");
	//		sb.append("WHERE __hibernate_row_nr__ BETWEEN ? AND ?");
	//
	//		return sb.toString();
	//	}
	//
	//	/**
	//	 * Utility method that checks if the given sql query is a select distinct one and if so replaces the distinct select
	//	 * with an equivalent simple select with a group by clause. See
	//	 * {@link SQLServer2005DialectTestCase#testReplaceDistinctWithGroupBy()}
	//	 * 
	//	 * @param sql an sql query
	//	 */
	//	protected static void replaceDistinctWithGroupBy (StringBuilder sql)
	//	{
	//		int distinctIndex = sql.indexOf(DISTINCT);
	//		if (distinctIndex > 0)
	//		{
	//			sql.delete(distinctIndex, distinctIndex + DISTINCT.length() + 1);
	//			sql.append(" group by").append(getSelectFieldsWithoutAliases(sql));
	//		}
	//	}
	//
	//	protected static CharSequence getSelectFieldsWithoutAliases (StringBuilder sql)
	//	{
	//		String select = sql.substring(sql.indexOf(SELECT) + SELECT.length(), sql.indexOf(FROM));
	//
	//		// Strip the as clauses
	//		return stripAliases(select);
	//	}
	//
	//
	//	/* (non-Javadoc)
	//	 * @see org.hibernate.dialect.Dialect#getSequenceNextValString(java.lang.String)
	//	 */
	//	@Override
	//	public String getSequenceNextValString (String sequenceName)
	//	{
	//		return "select " + getSelectSequenceNextValString(sequenceName);
	//	}
	//
	//	/* (non-Javadoc)
	//	 * @see org.hibernate.dialect.Dialect#getSelectSequenceNextValString(java.lang.String)
	//	 */
	//	@Override
	//	public String getSelectSequenceNextValString (String sequenceName)
	//	{
	//		return "next value for " + sequenceName;
	//	}
	//
	//	/* (non-Javadoc)
	//	 * @see org.hibernate.dialect.Dialect#getCreateSequenceString(java.lang.String)
	//	 */
	//	@Override
	//	public String getCreateSequenceString (String sequenceName)
	//	{
	//		return "create sequence " + sequenceName; //starts with 1, implicitly
	//	}
	//
	//	/* (non-Javadoc)
	//	 * @see org.hibernate.dialect.Dialect#getDropSequenceString(java.lang.String)
	//	 */
	//	@Override
	//	public String getDropSequenceString (String sequenceName)
	//	{
	//		return "drop sequence " + sequenceName;
	//	}
	//
	//	/* (non-Javadoc)
	//	 * @see org.hibernate.dialect.Dialect#supportsSequences()
	//	 */
	//	@Override
	//	public boolean supportsSequences ()
	//	{
	//		return true;
	//	}
	//
	//	/* (non-Javadoc)
	//	 * @see org.hibernate.dialect.Dialect#supportsPooledSequences()
	//	 */
	//	@Override
	//	public boolean supportsPooledSequences ()
	//	{
	//		return true;
	//	}
}
