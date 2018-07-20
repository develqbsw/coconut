package sk.qbsw.security.rest.oauth.client.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sk.qbsw.core.client.model.response.BaseResponseBody;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.CSVerificationTypes;

import javax.validation.constraints.NotNull;

/**
 * The authentication response.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode (callSuper = true)
public class VerificationResponseBody<T extends CSAccountData> extends BaseResponseBody
{
	private static final long serialVersionUID = 7533946963382197126L;

	@ApiModelProperty (required = true, value = "The account data")
	@NotNull
	private T accountData;

	@ApiModelProperty (required = true, value = "The verification type")
	@NotNull
	private CSVerificationTypes verificationType;
}
