package sk.qbsw.core.communication.mail.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import sk.qbsw.core.communication.mail.model.CAttachmentDefinition;
import sk.qbsw.core.communication.mail.model.domain.CAttachment;
import sk.qbsw.core.communication.mail.model.domain.CMail;
import sk.qbsw.core.communication.mail.model.domain.CRecipient;

/**
 * Abstract class for mail service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
public class AMailService
{
	/** The mail sender. */
	@Autowired
	protected JavaMailSender mailSender;

	/** Sender's email address. */
	protected String senderAdddress;

	/**
	 * Sets the smtp server.
	 *
	 * @param host the host
	 * @param port the port
	 */
	protected void setSMTPServer (String host, Integer port)
	{
		// setting mail sender parameters
		((org.springframework.mail.javamail.JavaMailSenderImpl) mailSender).setHost(host);
		((org.springframework.mail.javamail.JavaMailSenderImpl) mailSender).setPort(port);
	}

	/**
	 * Sets the sender address.
	 *
	 * @param senderAddress the new sender address
	 */
	protected void setSenderAddress (String senderAddress)
	{
		this.senderAdddress = senderAddress;
	}

	/**
	 * Create mail.
	 *
	 * @param to the recipient
	 * @param cc the cc recipient
	 * @param bcc the bcc recipient
	 * @param subject the subject
	 * @param body the body
	 * @param attachmentDefinitions the attachment definitions
	 * @return the c mail
	 * @throws IOException IOException
	 */
	protected CMail createMail (List<String> to, List<String> cc, List<String> bcc, String subject, String body, CAttachmentDefinition... attachmentDefinitions) throws IOException
	{
		//create mail
		CMail mail = new CMail();

		mail.setFrom(senderAdddress);
		if (to != null && to.size() > 0)
		{
			mail.setTo(new CRecipient(to));
		}
		if (cc != null && cc.size() > 0)
		{
			mail.setCc(new CRecipient(cc));
		}
		if (bcc != null && bcc.size() > 0)
		{
			mail.setBcc(new CRecipient(bcc));
		}
		mail.setSubject(subject);
		mail.setBody(body);

		//attachments
		if (attachmentDefinitions != null && attachmentDefinitions.length > 0)
		{
			Set<CAttachment> attachments = new HashSet<CAttachment>();

			//set attachments
			for (CAttachmentDefinition attachmentDefinition : attachmentDefinitions)
			{
				//create attachment from definition
				CAttachment attachment = new CAttachment();
				attachment.setMail(mail);
				attachment.setFileName(attachmentDefinition.getFileName());
				attachment.setData(IOUtils.toByteArray(attachmentDefinition.getDataStream()));
				attachment.setContentType(attachmentDefinition.getContentType());

				//add attachment to set
				attachments.add(attachment);
			}

			//set attachment set to mail
			mail.setAttachments(attachments);
		}

		return mail;
	}
}
