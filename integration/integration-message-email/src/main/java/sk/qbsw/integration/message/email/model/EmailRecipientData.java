package sk.qbsw.integration.message.email.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.integration.message.base.model.MessageTypes;
import sk.qbsw.integration.message.model.RecipientDataBase;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * The email recipient data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class EmailRecipientData extends RecipientDataBase
{
	private static final long serialVersionUID = 4839770764179436516L;

	@NotNull
	private List<String> to;

	private List<String> cc;

	private List<String> bcc;

	/**
	 * Instantiates a new Email recipient data.
	 *
	 * @param sender the sender
	 * @param subject the subject
	 * @param eventId the event id
	 * @param validityStart the validity start
	 * @param to the to
	 * @param cc the cc
	 * @param bcc the bcc
	 */
	@Builder
	public EmailRecipientData (String sender, String subject, Long eventId, OffsetDateTime validityStart, List<String> to, List<String> cc, List<String> bcc)
	{
		super(sender, subject, eventId, validityStart);
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
	}

	@Override
	public String getType ()
	{
		return MessageTypes.EMAIL;
	}
}
