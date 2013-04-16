package sk.qbsw.core.persistence.test.dao;

import javax.persistence.EntityManager;
import static org.mockito.Mockito.*;
import org.junit.Test;

import sk.qbsw.core.persistence.test.dao.mock.CTestDao;
import sk.qbsw.core.persistence.test.dao.mock.CTestEntity;
import sk.qbsw.core.persistence.test.dao.mock.ITestDao;

/**
 * Test for DAO generics
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 */
public class CDaoTestCase
{

	/**
	 * Test for Long generics.
	 */
	@Test
	public void testLong ()
	{
		ITestDao dao = new CTestDao();
		EntityManager manager = mock(EntityManager.class);
		when(manager.find(CTestEntity.class, 1l)).thenReturn(new CTestEntity(1l));
		((CTestDao) dao).setEntityManager(manager);

		CTestEntity entity = dao.findById(1l);
		System.out.println(entity.getId());
	}
}
