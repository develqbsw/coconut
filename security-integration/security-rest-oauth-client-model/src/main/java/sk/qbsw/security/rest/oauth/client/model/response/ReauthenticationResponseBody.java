package sk.qbsw.security.rest.oauth.client.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sk.qbsw.core.client.model.response.BaseResponseBody;
import sk.qbsw.security.rest.oauth.client.model.CSGeneratedTokenData;

import javax.validation.constraints.NotNull;

/**
 * The reauthentication response.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class ReauthenticationResponseBody extends BaseResponseBody
{
	private static final long serialVersionUID = 9094797792039380937L;

	@ApiModelProperty (required = true, value = "The authentication token data")
	@NotNull
	private CSGeneratedTokenData authenticationTokenData;
}
