package sk.qbsw.security.rest.oauth.client.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sk.qbsw.core.client.model.response.BaseResponseBody;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.CSGeneratedTokenData;

import javax.validation.constraints.NotNull;

/**
 * The authentication response.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class AuthenticationResponseBody<T extends CSAccountData> extends BaseResponseBody
{
	private static final long serialVersionUID = 7533946963382197126L;

	@ApiModelProperty (required = true, value = "The master token data")
	@NotNull
	private CSGeneratedTokenData masterTokenData;

	@ApiModelProperty (required = true, value = "The authentication token data")
	@NotNull
	private CSGeneratedTokenData authenticationTokenData;

	@ApiModelProperty (required = true, value = "The account data")
	@NotNull
	private T accountData;
}
