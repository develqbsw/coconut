package sk.qbsw.core.communication.mail;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.communication.mail.service.IMailService;

/**
 * The Class CTestEmailSend.
 * 
 * @author Dalibor Rak
 * @version 1.6.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
public class CTestEmailSend
{
	
	/** The mail service. */
	@Autowired
	private IMailService mailService;

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

	/**
	 * Test send mail.
	 */
	@Test
	public void testSendMail ()
	{
		mailService.setSenderAddress("noreply@qbsw.sk");
		mailService.setSMTPServer("195.168.42.242", 25);

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("name", "Dalibor");

		mailService.sendEmail("rak@qbsw.sk", "test", getInputStream("/email/test_en.vm"), values);
	}

	/**
	 * Test wrong tempplate.
	 */
	@Test (expected = CSystemException.class)
	public void testWrongTempplate ()
	{
		mailService.setSenderAddress("noreply@qbsw.sk");
		mailService.setSMTPServer("195.168.42.242", 25);

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("name", "Dalibor");

		mailService.sendEmail("rak@qbsw.sk", "test", getInputStream("/email/test_en1.vm"), values);
	}

}
