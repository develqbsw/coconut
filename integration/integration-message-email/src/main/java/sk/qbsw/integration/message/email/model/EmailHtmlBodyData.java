package sk.qbsw.integration.message.email.model;

import lombok.*;
import sk.qbsw.integration.message.model.BodyDataBase;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.Map;

/**
 * The email HTML body data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode (callSuper = true)
public class EmailHtmlBodyData extends BodyDataBase
{
	private static final long serialVersionUID = -1453271252558675252L;

	@NotNull
	private InputStream template;

	@NotNull
	private Map<String, Object> params;
}
