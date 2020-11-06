package sk.qbsw.security.rest.oauth.client.model.request;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.request.BaseRequestBody;

/**
 * Request for verifying if user is authorized with token.
 *
 * @author Jana Branisova
 * @version 2.6.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class VerifyRequestBody extends BaseRequestBody
{
	private static final long serialVersionUID = -1247837645013896288L;

	@Schema (required = true, description = "The security token")
	@NotNull
	private String token;

	@Schema (required = true, description = "The device id")
	@NotNull
	private String deviceId;

	@Schema (required = true, description = "The IP address")
	private String ip;
}
