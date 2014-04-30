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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.communication.mail.model.CAttachmentDefinition;
import sk.qbsw.core.communication.mail.service.IMailService;
import sk.qbsw.core.communication.mail.service.ITemplateBuilder;

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
public class CTestEmailSend
{
	/** The mail service. */
	@Autowired
	@Qualifier ("cMailService")
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

	/**
	 * Test send mail.
	 */
	@SuppressWarnings ("deprecation")
	@Test
	@Ignore
	public void deprecatedTestSendMail ()
	{
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("name", "Dalibor");

		mailService.sendEmail("lauro@qbsw.sk", "test", getInputStream("/email/test_en.vm"), values);
	}

	/**
	 * Test wrong template.
	 */
	@SuppressWarnings ("deprecation")
	@Test (expected = CSystemException.class)
	@Ignore
	public void testWrongTemplate ()
	{
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("name", "Dalibor");

		mailService.sendEmail("lauro@qbsw.sk", "test", getInputStream("/email/test_en1.vm"), values);
	}

	/**
	 * Test send mail to recipient.
	 * @throws UnsupportedEncodingException unsupported encoding
	 */
	@Test
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
