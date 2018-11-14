package sk.qbsw.security.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The result of token generation.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.18.2
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedTokenData implements Serializable
{
	private static final long serialVersionUID = -7328502942202337268L;

	@NotNull
	private String generatedToken;

	private String invalidatedToken;
}
