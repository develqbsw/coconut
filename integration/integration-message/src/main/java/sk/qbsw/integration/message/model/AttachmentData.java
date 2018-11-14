package sk.qbsw.integration.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The attachment data.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentData implements Serializable
{
	private static final long serialVersionUID = 1134392688462615279L;

	@NotNull
	private String fileName;

	@NotNull
	private String originalFileName;
}
