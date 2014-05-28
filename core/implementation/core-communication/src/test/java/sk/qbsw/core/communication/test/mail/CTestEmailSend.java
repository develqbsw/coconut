package sk.qbsw.core.communication.test.mail;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.communication.mail.dao.IMailDao;
import sk.qbsw.core.communication.mail.model.CAttachmentDefinition;
import sk.qbsw.core.communication.mail.model.domain.CMail;
import sk.qbsw.core.communication.mail.model.domain.EMailState;
import sk.qbsw.core.communication.mail.service.IMailService;
import sk.qbsw.core.communication.test.mail.util.CTestMailBuilder;

/**
 * The Class CTestEmailSend.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.9.1
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
@Ignore
public class CTestEmailSend
{
	/** The mail service. */
	@Autowired
	@Qualifier ("cMailService")
	private IMailService mailService;

	/** The mail builder. */
	@Autowired
	private CTestMailBuilder mailBuilder;

	/** The jpa mail dao. */
	@Autowired
	@Qualifier ("jpaMailDao")
	private IMailDao mailDao;

	/**
	 * Inits the mail service.
	 */
	@Before
	public void init ()
	{
		mailService.setSenderAddress("noreply@qbsw.sk");
		mailService.setSMTPServer("195.168.42.242", 25);
	}

	/**
	 * Test send mail.
	 */
	@SuppressWarnings ("deprecation")
	@Test
	@Ignore
	public void deprecatedTestSendMail ()
	{
		Map<String, Object> templateParams = mailBuilder.buildTemplateParams();

		mailService.sendEmail("lauro@qbsw.sk", "test", mailBuilder.getInputStream("/email/test_en.vm"), templateParams);
	}

	/**
	 * Test wrong template.
	 */
	@SuppressWarnings ("deprecation")
	@Test (expected = CSystemException.class)
	@Ignore
	public void testWrongTemplate ()
	{
		Map<String, Object> templateParams = mailBuilder.buildTemplateParams();

		mailService.sendEmail("lauro@qbsw.sk", "test", mailBuilder.getInputStream("/email/test_en1.vm"), templateParams);
	}

	/**
	 * Test send mail to recipient.
	 * @throws IOException 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testSendMailToRecipient () throws IOException
	{
		//create data
		List<String> recipients = mailBuilder.buildRecipients();
		String subject = mailBuilder.buildSubject();
		String body = mailBuilder.buildBody();

		//send
		mailService.setArchive(false);
		mailService.sendMail(recipients, subject, body);
		mailService.setArchive(true);

		//checks result
		List<CMail> mails = mailDao.findBySubject(subject);

		Assert.assertNotNull("Mail to recipient failed, empty mail list returned", mails);
		Assert.assertTrue("Mail to recipient failed, there is not 0", mails.size() == 0);
	}

	/**
	 * Test send mail to recipient and archive.
	 * @throws IOException 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testSendMailToRecipientArchive () throws IOException
	{
		//create data
		List<String> recipients = mailBuilder.buildRecipients();
		String subject = mailBuilder.buildSubject();
		String body = mailBuilder.buildBody();

		//send
		mailService.sendMail(recipients, subject, body);

		//checks result
		List<CMail> mails = mailDao.findBySubject(subject);

		Assert.assertNotNull("Mail to recipient failed, empty mail list returned", mails);
		Assert.assertTrue("Mail to recipient failed, there is more or less than 1 mail", mails.size() == 1);
		Assert.assertEquals("Mail to recipient failed, the mail has incorrect state", mails.get(0).getState(), EMailState.SENT);
	}

	/**
	 * Test send mail to recipient and archive with error
	 * @throws IOException 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testSendMailToRecipientArchiveError () throws IOException
	{
		mailService.setSMTPServer("195.168.142.242", 25);

		//create data
		List<String> recipients = mailBuilder.buildRecipients();
		String subject = mailBuilder.buildSubject();
		String body = mailBuilder.buildBody();

		//send
		try
		{
			mailService.sendMail(recipients, subject, body);
		}
		catch (Throwable e)
		{

		}

		//checks result
		List<CMail> mails = mailDao.findBySubject(subject);

		Assert.assertNotNull("Mail to recipient failed, empty mail list returned", mails);
		Assert.assertTrue("Mail to recipient failed, there is more or less than 1 mail", mails.size() == 1);
		Assert.assertEquals("Mail to recipient failed, the mail has incorrect state", mails.get(0).getState(), EMailState.COMMUNICATION_ERROR);

		mailService.setSMTPServer("195.168.42.242", 25);
	}

	/**
	 * Test send mail with attachment to recipient.
	 * @throws IOException 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testSendMailWithAttachmentToRecipient () throws IOException
	{
		//create data
		List<String> recipients = mailBuilder.buildRecipients();
		String subject = mailBuilder.buildSubject();
		String body = mailBuilder.buildBody();
		CAttachmentDefinition[] attachmentDefinitions = mailBuilder.buildAttachments();

		try
		{
			//send
			mailService.sendMail(recipients, subject, body, attachmentDefinitions);
		}
		finally
		{
			mailBuilder.closeAttachmentDefinitionStreams(attachmentDefinitions);
		}

		//checks result
		List<CMail> mails = mailDao.findBySubject(subject);

		Assert.assertNotNull("Mail to recipient failed, empty mail list returned", mails);
		Assert.assertTrue("Mail to recipient failed, there is more or less than 1 mail", mails.size() == 1);
		Assert.assertEquals("Mail to recipient failed, the mail has incorrect state", mails.get(0).getState(), EMailState.SENT);
	}
}
