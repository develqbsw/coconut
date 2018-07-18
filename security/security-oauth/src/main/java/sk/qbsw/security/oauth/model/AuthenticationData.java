package sk.qbsw.security.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qbsw.core.security.base.model.AccountData;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The authentication data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.2
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationData<D extends AccountData> implements Serializable
{
	private static final long serialVersionUID = 682928597886199156L;

	@NotNull
	private GeneratedTokenData masterTokenData;

	@NotNull
	private GeneratedTokenData authenticationTokenData;

	@NotNull
	private D accountData;
}
