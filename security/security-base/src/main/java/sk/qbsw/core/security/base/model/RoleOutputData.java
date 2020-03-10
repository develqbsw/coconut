package sk.qbsw.core.security.base.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The role output data.
 *
 * @author Tomas Lauro
 * @version 2.5.0
 * @since 2.5.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleOutputData implements Serializable
{
	private static final long serialVersionUID = -7233885490976805740L;

	@NotNull
	private Long id;

	@NotNull
	private String code;
}
