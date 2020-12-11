package sk.qbsw.security.rest.oauth.client.model;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.BaseClientEntity;

/**
 * The client side generated token data.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSGeneratedTokenData extends BaseClientEntity
{
	private static final long serialVersionUID = -3487173802800870802L;

	@Schema (required = true, description = "The generated token")
	@NotNull
	private String generatedToken;

	@Schema (description = "The invalidated token")
	private String invalidatedToken;
}
