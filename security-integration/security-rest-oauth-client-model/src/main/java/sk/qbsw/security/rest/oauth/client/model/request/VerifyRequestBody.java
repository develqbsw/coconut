package sk.qbsw.security.rest.oauth.client.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sk.qbsw.core.client.model.request.BaseRequestBody;

import javax.validation.constraints.NotNull;

/**
 * Request for verifying if user is authorized with token.
 *
 * @author Jana Branisova
 * @version 1.19.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode (callSuper = true)
public class VerifyRequestBody extends BaseRequestBody
{
	private static final long serialVersionUID = -1247837645013896288L;

	@ApiModelProperty (required = true, value = "The security token")
	@NotNull
	private String token;

	@ApiModelProperty (required = true, value = "The device id")
	@NotNull
	private String deviceId;

	@ApiModelProperty (required = true, value = "The IP address")
	private String ip;
}
