package sk.qbsw.core.communication.mail.model.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The mail saved in the database.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.9.1
 * @since 1.9.0
 */
@Entity
@Table (name = "t_mail", schema = "apsys")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue ("Mail")
public class CMail implements Serializable, IEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The entity id.
	 */
	@Id
	@SequenceGenerator (name = "t_mail_pkid_generator", sequenceName = "apsys.t_mail_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_mail_pkid_generator")
	@Column (name = "pk_id", nullable = true)
	private Long id;

	/** The from. */
	@Column (name = "c_from", nullable = false)
	private String from;

	/** The to. */
	@Column (name = "c_to")
	@Type (type = "sk.qbsw.core.communication.mail.model.domain.type.CRecipientType")
	private CRecipient to;

	/** The cc. */
	@Column (name = "c_cc")
	@Type (type = "sk.qbsw.core.communication.mail.model.domain.type.CRecipientType")
	private CRecipient cc;

	/** The bcc. */
	@Column (name = "c_bcc")
	@Type (type = "sk.qbsw.core.communication.mail.model.domain.type.CRecipientType")
	private CRecipient bcc;

	/** The subject. */
	@Column (name = "c_subject")
	private String subject;

	/** The body. */
	@Column (name = "c_body", length = 1024)
	private String body;

	/** Set of attachment. */
	@OneToMany (mappedBy = "mail", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<CAttachment> attachments;

	/** The mail sending state. */
	@Column (name = "c_state", nullable = false)
	@Enumerated (EnumType.STRING)
	private EMailState state = EMailState.UNSENT;

	/** The attempt counter for sending. */
	@Column (name = "c_attempt_counter", nullable = false)
	private int attemptCounter = 0;

	/** The date and time when the mail was created. */
	@Column (name = "c_created")
	@Type (type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime created;

	/** The date and time when the mail was sent. */
	@Column (name = "c_sent")
	@Type (type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime sent;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.model.domain.IEntity#getId()
	 */
	@Override
	public Long getId ()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId (Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the from.
	 *
	 * @return the from
	 */
	public String getFrom ()
	{
		return from;
	}

	/**
	 * Sets the from.
	 *
	 * @param from the new from
	 */
	public void setFrom (String from)
	{
		this.from = from;
	}

	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public CRecipient getTo ()
	{
		return to;
	}

	/**
	 * Sets the to.
	 *
	 * @param to the new to
	 */
	public void setTo (CRecipient to)
	{
		this.to = to;
	}

	/**
	 * Gets the cc.
	 *
	 * @return the cc
	 */
	public CRecipient getCc ()
	{
		return cc;
	}

	/**
	 * Sets the cc.
	 *
	 * @param cc the new cc
	 */
	public void setCc (CRecipient cc)
	{
		this.cc = cc;
	}

	/**
	 * Gets the bcc.
	 *
	 * @return the bcc
	 */
	public CRecipient getBcc ()
	{
		return bcc;
	}

	/**
	 * Sets the bcc.
	 *
	 * @param bcc the new bcc
	 */
	public void setBcc (CRecipient bcc)
	{
		this.bcc = bcc;
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject ()
	{
		return subject;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject the new subject
	 */
	public void setSubject (String subject)
	{
		this.subject = subject;
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public String getBody ()
	{
		return body;
	}

	/**
	 * Sets the body.
	 *
	 * @param body the new body
	 */
	public void setBody (String body)
	{
		this.body = body;
	}

	/**
	 * Gets the attachments.
	 *
	 * @return the attachments
	 */
	public Set<CAttachment> getAttachments ()
	{
		return attachments;
	}

	/**
	 * Sets the attachments.
	 *
	 * @param attachments the new attachments
	 */
	public void setAttachments (Set<CAttachment> attachments)
	{
		this.attachments = attachments;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public EMailState getState ()
	{
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState (EMailState state)
	{
		this.state = state;
	}

	/**
	 * Gets the attempt counter.
	 *
	 * @return the attempt counter
	 */
	public int getAttemptCounter ()
	{
		return attemptCounter;
	}

	/**
	 * Sets the attempt counter.
	 *
	 * @param attemptCounter the new attempt counter
	 */
	public void setAttemptCounter (int attemptCounter)
	{
		this.attemptCounter = attemptCounter;
	}

	/**
	 * Gets the created.
	 *
	 * @return the created
	 */
	public DateTime getCreated ()
	{
		return created;
	}

	/**
	 * Sets the created.
	 *
	 * @param created the new created
	 */
	public void setCreated (DateTime created)
	{
		this.created = created;
	}

	/**
	 * Gets the sent.
	 *
	 * @return the sent
	 */
	public DateTime getSent ()
	{
		return sent;
	}

	/**
	 * Sets the sent.
	 *
	 * @param sent the new sent
	 */
	public void setSent (DateTime sent)
	{
		this.sent = sent;
	}
}
