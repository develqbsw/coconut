package sk.qbsw.integration.mail.test;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.integration.mail.dao.IMailDao;
import sk.qbsw.integration.mail.model.CAttachmentDefinition;
import sk.qbsw.integration.mail.model.domain.CMail;
import sk.qbsw.integration.mail.service.IMailService;
import sk.qbsw.integration.mail.test.util.CTestMailBuilder;

/**
 * The background sending test.
 * 
 * @author Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback (true)
public class CEmailBackgroundSendTestCase
{
	@Autowired
	@Qualifier ("mailBackgroundService")
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

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testSendMailToRecipient () throws IOException
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
	}

	/**
	 * Test send mail with attachment to recipient.
	 * @throws IOException 
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
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

			//checks result
			List<CMail> mails = mailDao.findBySubject(subject);

			Assert.assertNotNull("Mail to recipient with attachent failed, empty mail list returned", mails);
			Assert.assertTrue("Mail to recipient with attachent failed, there is more or less than 1 mail", mails.size() == 1);
			Assert.assertNotNull("Mail to recipient with attachent failed, empty attachment list returned", mails.get(0).getAttachments());
			Assert.assertTrue("Mail to recipient with attachent failed, there is more or less than 1 attachemnt", mails.get(0).getAttachments().size() == 1);
		}
		finally
		{
			mailBuilder.closeAttachmentDefinitionStreams(attachmentDefinitions);
		}
	}
}
