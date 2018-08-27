package sk.qbsw.integration.message.service;

import sk.qbsw.integration.message.exception.InvalidMessageDataException;
import sk.qbsw.integration.message.model.AttachmentData;
import sk.qbsw.integration.message.model.BodyDataBase;
import sk.qbsw.integration.message.model.Message;
import sk.qbsw.integration.message.model.RecipientDataBase;

/**
 * The sendMessage message service.
 *
 * @param <M> the message type
 * @param <R> the recipient type
 * @param <B> the body type
 * @param <A> the attachment type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface SendMessageService<M extends Message, R extends RecipientDataBase, B extends BodyDataBase, A extends AttachmentData>
{
	/**
	 * Send message.
	 *
	 * @param recipient the recipient
	 * @param body the body
	 * @param attachments the attachments
	 * @return the long
	 * @throws InvalidMessageDataException the invalid message data exception
	 */
	M sendMessage (R recipient, B body, A... attachments) throws InvalidMessageDataException;
}
