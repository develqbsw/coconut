package sk.qbsw.security.spring.iam.auth.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The type Iam auth data.
 *
 * @author Tomas Leken
 * @version 2.1.0
 * @since 2.1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IAMAuthData implements Serializable
{
	private static final long serialVersionUID = -1319347875521577502L;
	
	@NotNull
	private String uid;

	@NotNull
	private String token;
}
