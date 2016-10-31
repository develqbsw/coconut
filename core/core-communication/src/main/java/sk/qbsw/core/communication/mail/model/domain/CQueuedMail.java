package sk.qbsw.core.communication.mail.model.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The queued mail saved in the database.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.9.1
 * @since 1.9.1
 */
@Entity
@DiscriminatorValue ("QueuedMail")
public class CQueuedMail extends CMail
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
}
