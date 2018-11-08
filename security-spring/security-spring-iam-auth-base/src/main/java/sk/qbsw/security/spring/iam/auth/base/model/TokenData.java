package sk.qbsw.security.spring.iam.auth.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * The type Token data.
 *
 * @param <T> the type parameter
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
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