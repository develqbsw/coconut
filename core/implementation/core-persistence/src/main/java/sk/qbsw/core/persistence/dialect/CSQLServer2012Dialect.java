package sk.qbsw.core.persistence.dialect;

import org.hibernate.dialect.SQLServer2008Dialect;

/**
 * MS SQL server 2012 hibernate dialekt
 * 
 * podporuje sekvencie ktore poskytuje najnovsi MS SQL server.
 *
 * @author martinkovic
 * @author Tomas Lauro
 * @version 1.6.1
 * @since 1.6.0
 */
public class CSQLServer2012Dialect extends SQLServer2008Dialect
{
	/* (non-Javadoc)
	 * @see org.hibernate.dialect.Dialect#getQuerySequencesString()
	 */
	@Override
	public String getQuerySequencesString ()
	{
		return "select name from sys.sequences";
	}

	/* (non-Javadoc)
	 * @see org.hibernate.dialect.Dialect#getSequenceNextValString(java.lang.String)
	 */
	@Override
	public String getSequenceNextValString (String sequenceName)
	{
		return "select " + getSelectSequenceNextValString(sequenceName);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.dialect.Dialect#getSelectSequenceNextValString(java.lang.String)
	 */
	@Override
	public String getSelectSequenceNextValString (String sequenceName)
	{
		return "next value for " + sequenceName;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.dialect.Dialect#getCreateSequenceString(java.lang.String)
	 */
	@Override
	public String getCreateSequenceString (String sequenceName)
	{
		return "create sequence " + sequenceName; //starts with 1, implicitly
	}

	/* (non-Javadoc)
	 * @see org.hibernate.dialect.Dialect#getDropSequenceString(java.lang.String)
	 */
	@Override
	public String getDropSequenceString (String sequenceName)
	{
		return "drop sequence " + sequenceName;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.dialect.Dialect#supportsSequences()
	 */
	@Override
	public boolean supportsSequences ()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.dialect.Dialect#supportsPooledSequences()
	 */
	@Override
	public boolean supportsPooledSequences ()
	{
		return true;
	}
}
