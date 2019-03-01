package sk.qbsw.security.spring.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The user data.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserData implements Serializable
{
	private static final long serialVersionUID = 3630802904916410299L;

	@NotNull
	private Long id;
}
