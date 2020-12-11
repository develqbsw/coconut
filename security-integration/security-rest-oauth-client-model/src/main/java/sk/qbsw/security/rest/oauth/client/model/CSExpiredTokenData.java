package sk.qbsw.security.rest.oauth.client.model;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.BaseClientEntity;

/**
 * The client expired token data.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSExpiredTokenData extends BaseClientEntity
{
	private static final long serialVersionUID = 8195244714688360808L;

	@Schema (required = true, description = "The token value")
	@NotNull
	private String token;

	@Schema (required = true, description = "The device id")
	@NotNull
	private String deviceId;

	@Schema (description = "The ip address")
	private String ip;
}
