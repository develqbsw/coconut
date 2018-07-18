package sk.qbsw.security.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.qbsw.core.security.base.model.AccountData;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * The security token data.
 *
 * @param <D> the type parameter
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class SecurityTokenDataBase<D extends AccountData> implements Serializable
{
	@NotNull
	private Long id;

	@NotNull
	private OffsetDateTime created;

	@NotNull
	private OffsetDateTime lastAccessed;

	@NotNull
	private String token;

	private String ip;

	@NotNull
	private String deviceId;

	@NotNull
	private D accountData;
}
