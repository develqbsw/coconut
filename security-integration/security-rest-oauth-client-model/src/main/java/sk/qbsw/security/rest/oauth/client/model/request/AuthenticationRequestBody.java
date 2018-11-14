package sk.qbsw.security.rest.oauth.client.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sk.qbsw.core.client.model.request.BaseRequestBody;

import javax.validation.constraints.NotNull;

/**
 * The authentication request.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class AuthenticationRequestBody extends BaseRequestBody
{
	private static final long serialVersionUID = -8124532358965918386L;

	@ApiModelProperty (required = true, value = "The login")
	@NotNull
	private String login;

	@ApiModelProperty (required = true, value = "The password")
	@NotNull
	private String password;

	@ApiModelProperty (required = true, value = "The device id")
	@NotNull
	private String deviceId;
}
