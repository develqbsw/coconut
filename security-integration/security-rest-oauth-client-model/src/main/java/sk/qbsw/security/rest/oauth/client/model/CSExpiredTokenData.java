package sk.qbsw.security.rest.oauth.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sk.qbsw.core.client.model.BaseClientEntity;

import javax.validation.constraints.NotNull;

/**
 * The client expired token data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSExpiredTokenData extends BaseClientEntity
{
	private static final long serialVersionUID = 8195244714688360808L;

	@ApiModelProperty (required = true, value = "The token value")
	@NotNull
	private String token;

	@ApiModelProperty (required = true, value = "The device id")
	@NotNull
	private String deviceId;

	@ApiModelProperty (value = "The ip address")
	private String ip;
}
