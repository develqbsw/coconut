package sk.qbsw.security.rest.oauth.client.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sk.qbsw.core.client.model.request.BaseRequestBody;

import javax.validation.constraints.NotNull;

/**
 * The reauthentication request.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.18.2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class ReauthenticationRequestBody extends BaseRequestBody
{
	private static final long serialVersionUID = 7750372943261168607L;

	@ApiModelProperty (required = true, value = "The master token")
	@NotNull
	private String masterToken;

	@ApiModelProperty (required = true, value = "The device id")
	@NotNull
	private String deviceId;
}
