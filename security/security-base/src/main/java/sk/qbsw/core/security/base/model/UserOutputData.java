package sk.qbsw.core.security.base.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The user output data.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOutputData implements Serializable
{
	private static final long serialVersionUID = 3630802904916410299L;

	@NotNull
	private Long id;
}
