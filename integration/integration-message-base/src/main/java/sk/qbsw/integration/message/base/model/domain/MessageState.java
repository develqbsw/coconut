package sk.qbsw.integration.message.base.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.persistence.model.domain.AEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

/**
 * The message state.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */

@Entity
@Table (name = "t_message_state", schema = DatabaseSchemas.MESSAGING)
@Getter
@Setter
public class MessageState extends AEntity<Long>
{
	private static final long serialVersionUID = 5075001074019392691L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "messageStateSequenceGenerator")
	@SequenceGenerator (name = "messageStateSequenceGenerator", schema = DatabaseSchemas.MESSAGING, sequenceName = "s_message_state")
	@Column (name = "pk_id")
	private Long id;

	@Column (name = "c_created", updatable = false, nullable = false)
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	private OffsetDateTime created;

	@NotNull
	@Column (name = "c_state")
	@Enumerated (EnumType.STRING)
	private MessageStates state;

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_message")
	private MessageBase message;

	@PrePersist
	private void processPersist ()
	{
		created = OffsetDateTime.now();
	}
}
