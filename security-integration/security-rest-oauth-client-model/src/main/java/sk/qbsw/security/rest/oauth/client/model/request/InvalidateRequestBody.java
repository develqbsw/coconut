package sk.qbsw.security.rest.oauth.client.model.request;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.request.BaseRequestBody;

/**
 * The Invalidate request.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class InvalidateRequestBody extends BaseRequestBody
{
	private static final long serialVersionUID = -4789897540369584317L;

	@Schema (required = true, description = "The master token")
	@NotNull
	private String masterToken;

	@Schema (required = true, description = "The authentication token")
	@NotNull
	private String authenticationToken;

	@Schema (required = true, description = "The device id")
	@NotNull
	private String deviceId;
}
