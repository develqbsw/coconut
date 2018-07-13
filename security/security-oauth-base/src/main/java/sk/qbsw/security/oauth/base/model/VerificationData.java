package sk.qbsw.security.oauth.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The verification data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.2
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationData<D extends AccountData> implements Serializable
{
	private static final long serialVersionUID = 2361821659986420969L;

	@NotNull
	private D accountData;

	@NotNull
	private VerificationTypes verificationType;
}
