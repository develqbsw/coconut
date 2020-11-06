package sk.qbsw.security.rest.oauth.client.model.request;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.request.BaseRequestBody;

/**
 * The authentication request.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class AuthenticationRequestBody extends BaseRequestBody
{
	private static final long serialVersionUID = -8124532358965918386L;

	@Schema (required = true, description = "The login")
	@NotNull
	private String login;

	@Schema (required = true, description = "The password")
	@NotNull
	private String password;

	@Schema (required = true, description = "The device id")
	@NotNull
	private String deviceId;
}
