package sk.qbsw.core.persistence.test.dao.mock;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;

/**
 * Testing DAO implementation.
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 */
public class CTestDao extends AEntityJpaDao<Long, CTestEntity> implements ITestDao
{
	/**
	 * Instantiates a new c test dao.
	 */
	public CTestDao ()
	{
		super(CTestEntity.class);
	}
}
