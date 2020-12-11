package sk.qbsw.security.rest.oauth.client.model.response;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.response.BaseResponseBody;
import sk.qbsw.security.rest.oauth.client.model.CSGeneratedTokenData;

/**
 * The reauthentication response.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class ReauthenticationResponseBody extends BaseResponseBody
{
	private static final long serialVersionUID = 9094797792039380937L;

	@Schema (required = true, description = "The authentication token data")
	@NotNull
	private CSGeneratedTokenData authenticationTokenData;
}
