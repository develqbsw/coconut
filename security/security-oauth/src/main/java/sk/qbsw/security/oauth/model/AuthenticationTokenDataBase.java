package sk.qbsw.security.oauth.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.security.base.model.AccountData;

import java.time.OffsetDateTime;

/**
 * The base authentication token data.
 *
 * @param <D> the account data type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode (callSuper = true)
public abstract class AuthenticationTokenDataBase<D extends AccountData>extends SecurityTokenDataBase<D>
{
	private static final long serialVersionUID = 8799094010842239348L;

	/**
	 * Instantiates a new Authentication token base.
	 *
	 * @param id the id
	 * @param created the created
	 * @param lastAccessed the last accessed
	 * @param token the token
	 * @param ip the ip
	 * @param deviceId the device id
	 * @param accountData the account data
	 */
	public AuthenticationTokenDataBase (Long id, OffsetDateTime created, OffsetDateTime lastAccessed, String token, String ip, String deviceId, D accountData)
	{
		super(id, created, lastAccessed, token, ip, deviceId, accountData);
	}
}
