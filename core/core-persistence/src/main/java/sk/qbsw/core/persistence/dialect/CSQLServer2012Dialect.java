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

}
