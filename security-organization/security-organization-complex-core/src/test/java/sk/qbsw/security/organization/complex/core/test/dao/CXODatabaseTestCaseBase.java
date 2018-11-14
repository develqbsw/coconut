package sk.qbsw.security.organization.complex.core.test.dao;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sk.qbsw.security.organization.complex.core.test.util.DataGenerator;

import static org.junit.Assert.assertNotNull;

/**
 * The abstract databse test case class.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
public abstract class CXODatabaseTestCaseBase
{
	/**
	 * The Data generator.
	 */
	@Autowired
	protected DataGenerator dataGenerator;

	/**
	 * Test initialization.
	 */
	protected void testInitialization ()
	{
		assertNotNull("Could not find data generator", dataGenerator);
	}

	/**
	 * Init test.
	 */
	protected void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
