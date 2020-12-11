package sk.qbsw.security.rest.oauth.client.model.response;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.response.BaseResponseBody;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.CSVerificationTypes;

/**
 * The authentication response.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class VerificationResponseBody<T extends CSAccountData>extends BaseResponseBody
{
	private static final long serialVersionUID = 7533946963382197126L;

	@Schema (required = true, description = "The account data")
	@NotNull
	private T accountData;

	@Schema (required = true, description = "The verification type")
	@NotNull
	private CSVerificationTypes verificationType;
}
