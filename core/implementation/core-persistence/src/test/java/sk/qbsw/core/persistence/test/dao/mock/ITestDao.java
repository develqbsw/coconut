package sk.qbsw.core.persistence.test.dao.mock;

import sk.qbsw.core.persistence.dao.ICrudDao;
import sk.qbsw.core.persistence.dao.IEntityDao;

/**
 * Testing DAO interface
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 */
public interface ITestDao extends IEntityDao<Long, CTestEntity>, ICrudDao<Long, CTestEntity>
{
}
