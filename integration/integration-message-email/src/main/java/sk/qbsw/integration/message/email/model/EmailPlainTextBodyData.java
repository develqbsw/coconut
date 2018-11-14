package sk.qbsw.integration.message.email.model;

import lombok.*;
import sk.qbsw.integration.message.model.BodyDataBase;

import javax.validation.constraints.NotNull;

/**
 * The email plain text body data.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode (callSuper = true)
public class EmailPlainTextBodyData extends BodyDataBase
{
	private static final long serialVersionUID = -1453271252558675252L;

	@NotNull
	private String body;
}
