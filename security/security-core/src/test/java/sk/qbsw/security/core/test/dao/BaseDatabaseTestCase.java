package sk.qbsw.security.core.test.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sk.qbsw.security.core.test.util.DataGenerator;

/**
 * The abstract databse test case class.
 *
 * @autor Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback(true)
public abstract class BaseDatabaseTestCase
{
	/** The database data generator. */
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
	 * Inits the test.
	 */
	protected void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
