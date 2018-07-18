package sk.qbsw.security.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The expired token data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.2
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpiredTokenData implements Serializable
{
	private static final long serialVersionUID = 2420599237524476906L;

	@NotNull
	private String token;

	@NotNull
	private String deviceId;

	private String ip;
}
