package sk.qbsw.security.rest.oauth.client.model.response;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.response.BaseResponseBody;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.CSGeneratedTokenData;

/**
 * The authentication response.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class AuthenticationResponseBody<T extends CSAccountData>extends BaseResponseBody
{
	private static final long serialVersionUID = 7533946963382197126L;

	@Schema (required = true, description = "The master token data")
	@NotNull
	private CSGeneratedTokenData masterTokenData;

	@Schema (required = true, description = "The authentication token data")
	@NotNull
	private CSGeneratedTokenData authenticationTokenData;

	@Schema (required = true, description = "The account data")
	@NotNull
	private T accountData;
}
