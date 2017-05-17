package sk.qbsw.integration.mail.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.qbsw.integration.mail.model.CAttachmentDefinition;
import sk.qbsw.integration.mail.service.ITemplateBuilder;

/**
 * The test mails builder.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
@Component ("testMailBuilder")
public class CTestMailBuilder
{
	/** The template builder. */
	@Autowired
	private ITemplateBuilder templateBuilder;

	/**
	 * Builds the template params.
	 *
	 * @return the map of params
	 */
	public Map<String, Object> buildTemplateParams ()
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Jozko Mrkvicka");

		return params;
	}

	/**
	 * Builds the recipients.
	 *
	 * @return the list of recipients
	 */
	public List<String> buildRecipients ()
	{
		return Arrays.asList("lauro@qbsw.sk");
	}

	/**
	 * Builds the subject.
	 *
	 * @return the subject string
	 */
	public String buildSubject ()
	{
		return "unit_test_2684";
	}

	/**
	 * Builds the attachments.
	 *
	 * @return the attachment definitions
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public CAttachmentDefinition[] buildAttachments () throws UnsupportedEncodingException
	{
		CAttachmentDefinition attachmentDefinition = new CAttachmentDefinition();
		attachmentDefinition.setFileName("qbsw_logo.jpg");
		attachmentDefinition.setDataStream(getInputStream("/email/qbsw.jpg"));

		return new CAttachmentDefinition[] {attachmentDefinition};
	}

	/**
	 * Builds the body.
	 *
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String buildBody () throws IOException
	{
		InputStream templateStream = getInputStream("/email/test_en.vm");

		try
		{
			return templateBuilder.buildMailBody(templateStream, buildTemplateParams());
		}
		finally
		{
			templateStream.close();
		}
	}

	/**
	 * Close attachment definition streams.
	 *
	 * @param attachmentDefinitions the attachment definitions
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void closeAttachmentDefinitionStreams (CAttachmentDefinition[] attachmentDefinitions) throws IOException
	{
		for (CAttachmentDefinition attachmentDefinition : attachmentDefinitions)
		{
			if (attachmentDefinition.getDataStream() != null)
			{
				attachmentDefinition.getDataStream().close();
			}
		}
	}

	/**
	 * Gets the input stream.
	 *
	 * @param path the path
	 * @return the input stream
	 */
	public InputStream getInputStream (String path)
	{
		return this.getClass().getResourceAsStream(path);
	}
}
