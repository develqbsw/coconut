package sk.qbsw.security.spring.oauth.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The simplified organization.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthData implements Serializable
{
	private static final long serialVersionUID = -5207755893924605999L;

	@NotNull
	private String token;

	@NotNull
	private String deviceId;

	private String ip;
}
