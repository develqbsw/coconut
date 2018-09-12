package sk.qbsw.integration.message.base.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.base.state.*;
import sk.qbsw.core.persistence.model.domain.AEntity;
import sk.qbsw.integration.message.model.Message;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The message base.
 *
 * @author Tomas Lauro
 * @author farkas.roman
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
@Table (name = "t_message", schema = DatabaseSchemas.MESSAGING)
@Getter
@Setter
public abstract class MessageBase extends AEntity<Long> implements Message, Stateful<MessageStates>
{
	private static final long serialVersionUID = 711052349375860770L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "messageSequenceGenerator")
	@SequenceGenerator (name = "messageSequenceGenerator", sequenceName = DatabaseSchemas.MESSAGING + ".s_message")
	@Column (name = "pk_id")
	private Long id;

	@Column (name = "c_created", updatable = false, nullable = false)
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	private OffsetDateTime created;

	@NotNull
	@Column (name = "c_sender")
	private String sender;

	@Column (name = "c_subject", length = 2000)
	private String subject;

	@NotNull
	@Lob
	@Column (name = "c_body")
	private String body;

	@Column (name = "c_event_id")
	private Long eventId;

	@NotNull
	@Column (name = "c_validity_start")
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	private OffsetDateTime validityStart;

	@NotNull
	@Column (name = "c_send_attempt_count")
	private Integer sendAttemptCount = 0;

	@NotNull
	@OneToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_current_state")
	private MessageState currentState;

	@OneToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "message")
	private Set<MessageState> states = new HashSet<>();

	@Transient
	private transient SimpleStateMachine<MessageBase, MessageStates> stateMachine;

	@Transient
	private MessageStates state = MessageStates.NEW;

	@PrePersist
	private void processPersist ()
	{
		created = OffsetDateTime.now();
	}

	@PostLoad
	private void processLoad ()
	{
		state = currentState.getState();
	}

	public MessageBase ()
	{
		SimpleTransitionManager<MessageStates> transitionManager = new SimpleTransitionManagerImpl<>();
		transitionManager.addTransition(MessageStates.NEW, MessageStates.NEW);
		transitionManager.addTransition(MessageStates.NEW, MessageStates.SENT);
		transitionManager.addTransition(MessageStates.NEW, MessageStates.ERROR);
		transitionManager.addTransition(MessageStates.NEW, MessageStates.NETWORK_ERROR);
		transitionManager.addTransition(MessageStates.NEW, MessageStates.DELETED);
		transitionManager.addTransition(MessageStates.SENT, MessageStates.DELETED);
		transitionManager.addTransition(MessageStates.NETWORK_ERROR, MessageStates.SENT);

		this.stateMachine = new SimpleStateMachineImpl<>(transitionManager);
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public abstract String getType ();

	@Override
	public void changeState (MessageStates state)
	{
		// change state
		stateMachine.changeState(this, state);

		if (this.getCurrentState() == null)
		{
			MessageState newState = new MessageState();
			newState.setState(this.getState());

			this.setCurrentState(newState);
		}
		else
		{
			if (!state.equals(this.getCurrentState().getState()))
			{
				this.getCurrentState().setMessage(this);
				this.getStates().add(this.getCurrentState());

				MessageState newState = new MessageState();
				newState.setState(this.getState());

				this.setCurrentState(newState);
			}
		}
	}

	private OffsetDateTime getStateCreated (MessageStates state)
	{
		if (getCurrentState() != null && getCurrentState().getState().equals(state))
		{
			return getCurrentState().getCreated();
		}
		else
		{
			return this.states.stream().filter(s -> s.getState().equals(state)).findFirst().map(MessageState::getCreated).orElse(null);
		}
	}

	/**
	 * Gets new state create.
	 *
	 * @return the new state create
	 */
	public OffsetDateTime getNewStateCreated ()
	{
		return getStateCreated(MessageStates.NEW);
	}

	/**
	 * Gets sent state create.
	 *
	 * @return the sent state create
	 */
	public OffsetDateTime getSentStateCreated ()
	{
		return getStateCreated(MessageStates.SENT);
	}

	/**
	 * Gets deleted state create.
	 *
	 * @return the deleted state create
	 */
	public OffsetDateTime getDeletedStateCreated ()
	{
		return getStateCreated(MessageStates.DELETED);
	}

	/**
	 * Gets error state create.
	 *
	 * @return the error state create
	 */
	public OffsetDateTime getErrorStateCreated ()
	{
		return getStateCreated(MessageStates.ERROR);
	}
}
