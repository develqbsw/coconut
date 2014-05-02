package sk.qbsw.core.communication.test.mail;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.communication.mail.model.CAttachmentDefinition;
import sk.qbsw.core.communication.mail.service.IMailService;
import sk.qbsw.core.communication.test.mail.util.CTestMailBuilder;

/**
 * The Class CTestEmailSend.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
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
	public void testSendMailToRecipient () throws IOException
	{
		//create data
		List<String> recipients = mailBuilder.buildRecipients();
		String subject = mailBuilder.buildSubject();
		String body = mailBuilder.buildBody();

		//send
		mailService.sendMail(recipients, subject, body);
	}

	/**
	 * Test send mail with attachment to recipient.
	 * @throws IOException 
	 */
	@Test
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
	}
}
