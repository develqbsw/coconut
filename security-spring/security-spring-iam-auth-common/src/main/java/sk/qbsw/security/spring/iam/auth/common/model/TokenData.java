package sk.qbsw.security.spring.iam.auth.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * The type Token data.
 *
 * @param <T> the type parameter
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
@AllArgsConstructor
public class TokenData<T>
{
	@NotNull
	private String uid;

	@NotNull
	private String login;

	@NotNull
	private String email;

	@NotNull
	private T data;
}
