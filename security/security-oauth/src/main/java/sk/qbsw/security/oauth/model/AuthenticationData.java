package sk.qbsw.security.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class AuthenticationData implements Serializable
{
	private static final long serialVersionUID = 5207147948056566911L;

	@NotNull
	private GeneratedTokenData masterTokenData;

	@NotNull
	private GeneratedTokenData authenticationTokenData;

	@NotNull
	private AccountData accountData;
}
