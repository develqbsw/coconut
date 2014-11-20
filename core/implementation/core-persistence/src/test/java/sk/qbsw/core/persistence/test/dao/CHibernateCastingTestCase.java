package sk.qbsw.core.persistence.test.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import sk.qbsw.core.persistence.dao.hibernate.CHibernateUtils;
import sk.qbsw.core.persistence.test.dao.mock.CTestEntity;

/**
 * HibernateCasting test for casting of entity.
 * 
 * @author Dalibor Rak
 * @version 1.12.0
 * @since 1.12.0
 * 
 */
public class CHibernateCastingTestCase {

	/**
	 * Test deproxy for basic entity and validate it's ID.
	 */
	@Test
	public void testDeproxyForBasicEntity() {
		CTestEntity entity = new CTestEntity(1l);
		CTestEntity toCheck = CHibernateUtils.deproxy(entity, CTestEntity.class);

		// check IDs
		assertSame(toCheck.getId(), Long.valueOf(1l));
	}
}
