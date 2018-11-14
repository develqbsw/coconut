package sk.qbsw.integration.message.email.service;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * The email template builder implementation.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class EmailTemplateBuilderImpl implements EmailTemplateBuilder
{
	private static final String TEMPLATE_ENCODING = "UTF-8";

	private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss", Locale.GERMAN);

	@Override
	public String buildBody (InputStream template, Map<String, Object> params) throws UnsupportedEncodingException
	{
		// create shallow copy of map
		Map<String, Object> paramsShallowCopy = new HashMap<>(params);

		// add date to parameters
		paramsShallowCopy.put("date", OffsetDateTime.now().format(df));

		StringWriter writer = new StringWriter();
		VelocityContext context = new VelocityContext(paramsShallowCopy);
		Reader templateReader = new BufferedReader(new InputStreamReader(template, TEMPLATE_ENCODING));

		Velocity.evaluate(context, writer, "Velocity mail builder", templateReader);

		return writer.toString();
	}
}
