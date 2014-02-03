package sk.qbsw.core.communication.mail.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.exception.CSystemException;

/**
 * Mail sender.
 *
 * @author Jan Sivulka
 * @author Dalibor Rak
 * @since 1.6.0
 * @version 1.6.0
 */

@Service ("cMailService")
public class CMailSender implements IMailService
{

	/** The mail sender. */
	@Autowired
	private JavaMailSender mailSender;

	/** Sender's email address. */
	private String senderAdddress;

	/** Constructed email message. */
	private MimeMessage message;

	/** The helper. */
	private MimeMessageHelper helper;

	/** The df. */
	private DateTimeFormatter df = DateTimeFormat.shortDateTime().withLocale(Locale.GERMAN); // .forPattern("yyyy.MM.dd HH:mm:ss")

	/**
	 * Sets the mail sender.
	 * 
	 * @param mailSender
	 *            the new mail sender
	 */
	public void setMailSender (JavaMailSender mailSender)
	{
		this.mailSender = mailSender;
	}

	/**
	 * Gets the mail sender.
	 * 
	 * @return the mail sender
	 */
	public JavaMailSender getMailSender ()
	{
		return mailSender;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.IMailService#sendEmail(java.lang.String, java.lang.String, java.io.InputStream, java.util.Map)
	 */
	@Override
	public void sendEmail (String to, String subject, InputStream template, Map<String, Object> params)
	{
		if (to == null || to.length() == 0)
		{
			throw new InvalidParameterException("Recepient address not set");
		}

		try
		{
			message = mailSender.createMimeMessage();
			helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(senderAdddress);

			params.put("date", df.print(new DateTime()));

			// checks if to is filled
			helper.setTo(to);

			helper.setSubject(subject);

			// create body of email
			String body = createBody(template, params);

			helper.setText(body, true);

			mailSender.send(message);
		}
		catch (Throwable e)
		{
			Logger.getLogger(getClass()).error("Mail sending problem", e);
			throw new CSystemException("Mail sending problem", e);
		}
	}

	/**
	 * Creates the body.
	 *
	 * @param template the template
	 * @param parameters the parameters
	 * @return the string
	 * @throws UnsupportedEncodingException wrong encoding
	 */
	private String createBody (InputStream template, Map<String, Object> parameters) throws UnsupportedEncodingException
	{
		StringWriter writer = new StringWriter();
		VelocityContext context = new VelocityContext(parameters);
		Reader templateReader = new BufferedReader(new InputStreamReader(template, "UTF-8" ));

		Velocity.evaluate(context, writer, "log tag name", templateReader);

		return writer.toString();

	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.IMailService#setSMTPServer(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void setSMTPServer (String host, Integer port)
	{
		// setting mail sender parameters
		((org.springframework.mail.javamail.JavaMailSenderImpl) mailSender).setHost(host);
		((org.springframework.mail.javamail.JavaMailSenderImpl) mailSender).setPort(port);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.IMailService#setSenderAddress(java.lang.String)
	 */
	@Override
	public void setSenderAddress (String senderAddress)
	{
		this.senderAdddress = senderAddress;
	}
}
