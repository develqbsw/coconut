package sk.qbsw.core.communication.mail.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 * The template builder.
 *
 * @author Tomas Lauro
 * 
 * @since 1.9.0
 * @version 1.9.0
 */

@Component ("templateBuilder")
public class CTemplateBuilder implements ITemplateBuilder
{
	/** The template encoding. */
	private final String TEMPLATE_ENCODING = "UTF-8";

	/** The df. */
	private DateTimeFormatter df = DateTimeFormat.shortDateTime().withLocale(Locale.GERMAN); // .forPattern("yyyy.MM.dd HH:mm:ss")

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.ITemplateBuilder#buildMailBody(java.io.InputStream, java.util.Map)
	 */
	@Override
	public String buildMailBody (InputStream template, Map<String, Object> params) throws UnsupportedEncodingException
	{
		//create shallow copy of map
		Map<String, Object> paramsShallowCopy = new HashMap<String, Object>();
		paramsShallowCopy.putAll(params);

		//add date to parameters
		paramsShallowCopy.put("date", df.print(new DateTime()));

		StringWriter writer = new StringWriter();
		VelocityContext context = new VelocityContext(paramsShallowCopy);
		Reader templateReader = new BufferedReader(new InputStreamReader(template, TEMPLATE_ENCODING));

		Velocity.evaluate(context, writer, "log tag name", templateReader);

		return writer.toString();

	}
}
