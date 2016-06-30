package sk.qbsw.core.communication.test.mail;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.communication.mail.dao.IMailDao;
import sk.qbsw.core.communication.mail.exception.CCommunicationException;
import sk.qbsw.core.communication.mail.job.CSendMailTask;
import sk.qbsw.core.communication.mail.model.domain.CMail;
import sk.qbsw.core.communication.mail.model.domain.EMailState;
import sk.qbsw.core.communication.mail.service.IMailService;
import sk.qbsw.core.communication.test.mail.util.CTestMailBuilder;
import sk.qbsw.core.testing.mock.IMockHelper;

/**
 * The test for sending mail task.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.9.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback (true)
public class CSendMailTaskTestCase
{

	/** The mail sender. */
	@Autowired
	protected JavaMailSender mailSender;

	/** The mail service. */
	@Autowired
	@Qualifier ("mailBackgroundService")
	private IMailService mailService;

	/** The send mail task. */
	@Autowired
	private CSendMailTask sendMailTask;

	/** The mail builder. */
	@Autowired
	private CTestMailBuilder mailBuilder;

	/** The jpa mail dao. */
	@Autowired
	@Qualifier ("jpaMailDao")
	private IMailDao jpaMailDao;

	/** The mock sender mail dao. */
	@Autowired
	@Qualifier ("mockSenderMailDao")
	private IMailDao mockSenderMailDao;

	/** The mock helper. */
	@Autowired
	private IMockHelper mockHelper;

	/**
	 * Inits the mail service.
	 */
	@Before
	public void init ()
	{
		//MockitoAnnotations.initMocks(this);
		mailService.setSenderAddress("noreply@qbsw.sk");
		mailService.setSMTPServer("195.168.42.242", 25);
	}

	/**
	 * Test one communication failure.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testOneCommunicationFailure () throws Exception
	{
		//reset mockito
		Mockito.reset(mockSenderMailDao);

		//mockito rules
		Mockito.doThrow(CCommunicationException.class).when(mockSenderMailDao).update(Mockito.any(CMail.class));
		//mock sender dao
		ReflectionTestUtils.setField(mockHelper.unwrapSpringProxyObject(sendMailTask), "senderMailDao", mockSenderMailDao);

		//create test data
		createTestData();

		//send mails
		sendMailTask.run();

		//check results
		List<CMail> mails = jpaMailDao.findAllQueued(EMailState.UNSENT);

		Assert.assertNotNull("Background mail to recipient failed, empty mail list returned", mails);
		Assert.assertTrue("Background mail to recipient failed, there is more or less than 5 mail", mails.size() == 5);

		//checks if all mail has a 1 in attemptCounter
		for (CMail mail : mails)
		{
			Assert.assertTrue("Background mail to recipient failed, there is mail with attemptCounter not 1", mail.getAttemptCounter() == 1);
		}

	}

	/**
	 * Test success run.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testSuccessRun () throws Exception
	{
		//reset mockito
		Mockito.reset(mockSenderMailDao);

		//mockito rules
		Mockito.when(mockSenderMailDao.update(Mockito.any(CMail.class))).thenReturn(new CMail());

		//mock sender dao
		ReflectionTestUtils.setField(mockHelper.unwrapSpringProxyObject(sendMailTask), "senderMailDao", mockSenderMailDao);

		//create test data
		createTestData();

		//send mails
		sendMailTask.run();

		//check results
		List<CMail> mails = jpaMailDao.findAllQueued(EMailState.SENT);

		Assert.assertNotNull("Background mail to recipient failed, empty mail list returned", mails);
		Assert.assertTrue("Background mail to recipient failed, there is more or less than 5 mail", mails.size() == 5);
	}

	/**
	 * Test communication failure.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCommunicationFailure () throws Exception
	{
		//reset mockito
		Mockito.reset(mockSenderMailDao);

		//mockito rules
		Mockito.doThrow(CCommunicationException.class).when(mockSenderMailDao).update(Mockito.any(CMail.class));

		//mock sender dao
		ReflectionTestUtils.setField(mockHelper.unwrapSpringProxyObject(sendMailTask), "senderMailDao", mockSenderMailDao);

		//create test data
		createTestData();

		//send mails
		for (int i = 0; i < 5; i++)
		{
			sendMailTask.run();
		}

		//check results
		List<CMail> mails = jpaMailDao.findAllQueued(EMailState.COMMUNICATION_ERROR);

		Assert.assertNotNull("Background mail to recipient failed, empty mail list returned", mails);
		Assert.assertTrue("Background mail to recipient failed, there is more or less than 5 mail", mails.size() == 5);
	}

	/**
	 * Test data failure.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testDataFailure () throws Exception
	{
		//reset mockito
		Mockito.reset(mockSenderMailDao);

		//mockito rules
		Mockito.doThrow(CSystemException.class).when(mockSenderMailDao).update(Mockito.any(CMail.class));
		//mock sender dao
		ReflectionTestUtils.setField(mockHelper.unwrapSpringProxyObject(sendMailTask), "senderMailDao", mockSenderMailDao);

		//create test data
		createTestData();

		//send mails
		sendMailTask.run();

		//check results
		List<CMail> mails = jpaMailDao.findAllQueued(EMailState.DATA_ERROR);

		Assert.assertNotNull("Background mail to recipient failed, empty mail list returned", mails);
		Assert.assertTrue("Background mail to recipient failed, there is more or less than 5 mail", mails.size() == 5);
	}

	/**
	 * Creates the test data.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void createTestData () throws IOException
	{
		//create data
		List<String> recipients = mailBuilder.buildRecipients();
		String subject = mailBuilder.buildSubject();
		String body = mailBuilder.buildBody();

		//save to database
		for (int i = 0; i < 5; i++)
		{
			mailService.sendMail(recipients, subject, body);
		}
	}
}
