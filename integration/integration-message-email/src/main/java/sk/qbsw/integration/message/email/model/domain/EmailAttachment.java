package sk.qbsw.integration.message.email.model.domain;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.integration.message.base.model.domain.MessageAttachmentBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * The email attachment.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Entity
@Table (name = "t_email_attachment", schema = DatabaseSchemas.MESSAGING)
@Getter
@Setter
public class EmailAttachment extends MessageAttachmentBase
{
	private static final long serialVersionUID = 5259035160034126672L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "emailAttachmentSequenceGenerator")
	@SequenceGenerator (name = "emailAttachmentSequenceGenerator", sequenceName = DatabaseSchemas.MESSAGING + ".s_email_attachment")
	@Column (name = "pk_id")
	private Long id;

	@NotNull
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_email")
	private Email email;
}
