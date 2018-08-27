package sk.qbsw.integration.message.email.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.integration.message.base.model.MessageTypes;
import sk.qbsw.integration.message.base.model.domain.MessageBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * The email.
 *
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */

@Entity
@Table (name = "t_email", schema = DatabaseSchemas.MESSAGING)
@Getter
@Setter
public class Email extends MessageBase
{
	private static final long serialVersionUID = 711052349375860770L;

	@NotNull
	@Column (name = "c_to", length = 8000)
	@Type (type = "sk.qbsw.integration.message.email.model.domain.type.RecipientType")
	private Recipient to;

	@Column (name = "c_cc", length = 8000)
	@Type (type = "sk.qbsw.integration.message.email.model.domain.type.RecipientType")
	private Recipient cc;

	@Column (name = "c_bcc", length = 8000)
	@Type (type = "sk.qbsw.integration.message.email.model.domain.type.RecipientType")
	private Recipient bcc;

	@NotNull
	@Column (name = "c_html_format")
	private Boolean htmlFormat;

	@OneToMany (mappedBy = "email", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<EmailAttachment> attachments = new HashSet<>();

	@Override
	public String getType ()
	{
		return MessageTypes.EMAIL;
	}
}
