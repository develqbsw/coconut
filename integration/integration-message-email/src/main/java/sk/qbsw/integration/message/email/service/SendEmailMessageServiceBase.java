package sk.qbsw.integration.message.email.service;

import sk.qbsw.integration.message.base.model.domain.MessageStates;
import sk.qbsw.integration.message.email.model.EmailRecipientData;
import sk.qbsw.integration.message.email.model.domain.Email;
import sk.qbsw.integration.message.email.model.domain.EmailAttachment;
import sk.qbsw.integration.message.email.model.domain.Recipient;
import sk.qbsw.integration.message.model.AttachmentData;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The send email message service base.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public abstract class SendEmailMessageServiceBase
{
	/**
	 * Create email email.
	 *
	 * @param recipient the recipient
	 * @param body the body
	 * @param attachments the attachments
	 * @return the email
	 */
	protected Email createEmail (EmailRecipientData recipient, String body, boolean htmlFormat, AttachmentData... attachments)
	{
		Email email = new Email();
		email.setSender(recipient.getSender());
		email.setSubject(recipient.getSubject());
		email.setEventId(recipient.getEventId());
		email.setValidityStart(recipient.getValidityStart() != null ? recipient.getValidityStart() : OffsetDateTime.now());

		email.setTo(new Recipient(recipient.getTo()));
		email.setCc(recipient.getCc() != null && !recipient.getCc().isEmpty() ? new Recipient(recipient.getCc()) : null);
		email.setBcc(recipient.getBcc() != null && !recipient.getBcc().isEmpty() ? new Recipient(recipient.getBcc()) : null);

		email.setBody(body);
		if (attachments != null)
		{
			email.setAttachments(Arrays.stream(attachments).map(a -> createEmailAttachment(a, email)).collect(Collectors.toSet()));
		}

		email.setHtmlFormat(htmlFormat);
		email.changeState(MessageStates.NEW);

		return email;
	}

	/**
	 * Create email attachment email attachment.
	 *
	 * @param data the data
	 * @param email the email
	 * @return the email attachment
	 */
	protected EmailAttachment createEmailAttachment (AttachmentData data, Email email)
	{
		EmailAttachment attachment = new EmailAttachment();
		attachment.setFileName(data.getFileName());
		attachment.setOriginalFileName(data.getOriginalFileName());
		attachment.setEmail(email);

		return attachment;
	}
}
