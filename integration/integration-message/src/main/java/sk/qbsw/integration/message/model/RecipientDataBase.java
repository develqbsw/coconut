package sk.qbsw.integration.message.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * The recipient data base.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class RecipientDataBase implements Serializable
{
	private static final long serialVersionUID = 4839770764179436516L;

	@NotNull
	private String sender;

	private String subject;

	private Long eventId;

	@NotNull
	private OffsetDateTime validityStart;

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public abstract String getType ();
}
