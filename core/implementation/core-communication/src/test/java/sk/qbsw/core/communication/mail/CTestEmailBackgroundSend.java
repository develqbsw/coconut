package sk.qbsw.core.communication.mail;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import sk.qbsw.core.communication.mail.model.CAttachmentDefinition;
import sk.qbsw.core.communication.mail.service.IMailService;
import sk.qbsw.core.communication.mail.service.ITemplateBuilder;

/**
 * The background sending test.
 * 
 * @author Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = false)
public class CTestEmailBackgroundSend
{
	@Autowired
	@Qualifier ("mailBackgroundService")
	private IMailService mailService;

	/** The template builder. */
	@Autowired
	private ITemplateBuilder templateBuilder;

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
	@Transactional
	@Rollback (false)
	@Ignore
	public void test () throws InterruptedException
	{
		//		List<String> to = new ArrayList<String>();
		//		to.add("lauro@qbsw.sk");
		//		
		//		CMail mail = new CMail();
		//		mail.setFrom("noreply@qbsw.sk");
		//		mail.setTo(new CRecipient(to));
		//		mail.setSubject("test");
		//		mail.setBody("Test");
		//		
		//		CAttachment attachment = new CAttachment();
		//		attachment.setFileName("name.txt");
		//		attachment.setData(new byte[]{'a'});
		//		attachment.setMail(mail);
		//		
		//		Set<CAttachment> attachments = new HashSet<CAttachment>();
		//		attachments.add(attachment);
		//		
		//		mail.setAttachments(attachments);
		//		
		//		jpaMailDao.save(mail);
		//		jpaMailDao.flush();
		//		jpaMailDao.clear();
		/*******/

		//		CMail mail = jpaMailDao.findById(250L);
		//		
		//		smtpMailDao.save(mail);

	}

	@Test
	@Transactional
	@Rollback (false)
	public void testSendMailToRecipient () throws UnsupportedEncodingException
	{
		//template params
		Map<String, Object> templateParams = new HashMap<String, Object>();
		templateParams.put("name", "Jozko Mrkvicka");

		//recipients
		List<String> recipients = Arrays.asList("lauro@qbsw.sk");

		//send
		mailService.sendMail(recipients, "test", templateBuilder.buildMailBody(getInputStream("/email/test_en.vm"), templateParams));
	}

	/**
	 * Test send mail with attachment to recipient.
	 * 
	 * @throws UnsupportedEncodingException unsupported encoding
	 */
	@Test
	public void testSendMailWithAttachmentToRecipient () throws UnsupportedEncodingException
	{
		//template params
		Map<String, Object> templateParams = new HashMap<String, Object>();
		templateParams.put("name", "Jozko Mrkvicka");

		//recipients
		List<String> recipients = Arrays.asList("lauro@qbsw.sk");

		//attachments
		CAttachmentDefinition attachmentDefinition = new CAttachmentDefinition();
		attachmentDefinition.setFileName("qbsw_logo.jpg");
		attachmentDefinition.setDataStream(getInputStream("/email/qbsw.jpg"));

		//send
		mailService.sendMail(recipients, "test with attachment", templateBuilder.buildMailBody(getInputStream("/email/test_en.vm"), templateParams), new CAttachmentDefinition[] {attachmentDefinition});
	}

	/**
	 * Gets the input stream.
	 *
	 * @param path the path
	 * @return the input stream
	 */
	private InputStream getInputStream (String path)
	{
		return this.getClass().getResourceAsStream(path);
	}
}
