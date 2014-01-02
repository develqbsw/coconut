package sk.qbsw.code.security.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import sk.qbsw.core.security.service.IAuthenticationService;

/**
 * Checks Authentication service
 * 
 * @version 1.6.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager")
public class CAuthenticationTestCase
{

	/** The authentication service. */
	@Autowired
	@Qualifier ("cLoginService")
	private IAuthenticationService authenticationService;

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Couldnt find Authentication service.", authenticationService);
	}

}
