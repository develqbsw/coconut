package sk.qbsw.core.persistence.dialect;

import org.hibernate.dialect.SQLServer2008Dialect;

/**
 * MS SQL server 2012 hibernate dialekt 
 * 
 * podporuje sekvencie ktore poskytuje najnovsi MS SQL server 
 * 
 * @author martinkovic
 * @version 1.0.0
 * @since 1.0.0
 */
public class CSQLServer2012Dialect extends SQLServer2008Dialect
{

	@Override
	public String getSequenceNextValString (String sequenceName)
	{
		return "select " + getSelectSequenceNextValString(sequenceName);
	}

	@Override
	public String getSelectSequenceNextValString (String sequenceName)
	{
		return "next value for " + sequenceName;
	}

	@Override
	public String getCreateSequenceString (String sequenceName)
	{
		return "create sequence " + sequenceName; //starts with 1, implicitly
	}

	@Override
	public String getDropSequenceString (String sequenceName)
	{
		return "drop sequence " + sequenceName;
	}

	@Override
	public boolean supportsSequences ()
	{
		return true;
	}

	@Override
	public boolean supportsPooledSequences ()
	{
		return true;
	}



}
