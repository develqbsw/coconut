package sk.qbsw.security.rest.oauth.client.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sk.qbsw.core.client.model.request.BaseRequestBody;

import javax.validation.constraints.NotNull;

/**
 * The Invalidate request.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class InvalidateRequestBody extends BaseRequestBody
{
	private static final long serialVersionUID = -4789897540369584317L;

	@ApiModelProperty (required = true, value = "The master token")
	@NotNull
	private String masterToken;

	@ApiModelProperty (required = true, value = "The authentication token")
	@NotNull
	private String authenticationToken;

	@ApiModelProperty (required = true, value = "The device id")
	@NotNull
	private String deviceId;
}
