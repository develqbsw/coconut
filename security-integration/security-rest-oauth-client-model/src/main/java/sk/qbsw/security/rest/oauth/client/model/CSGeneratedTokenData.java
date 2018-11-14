package sk.qbsw.security.rest.oauth.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sk.qbsw.core.client.model.BaseClientEntity;

import javax.validation.constraints.NotNull;

/**
 * The client side generated token data.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.18.2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSGeneratedTokenData extends BaseClientEntity
{
	private static final long serialVersionUID = -3487173802800870802L;

	@ApiModelProperty (required = true, value = "The generated token")
	@NotNull
	private String generatedToken;

	@ApiModelProperty (value = "The invalidated token")
	private String invalidatedToken;
}
