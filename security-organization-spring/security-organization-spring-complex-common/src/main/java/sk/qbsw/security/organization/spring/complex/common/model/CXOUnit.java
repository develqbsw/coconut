package sk.qbsw.security.organization.spring.complex.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The complex organization unit.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CXOUnit implements Serializable
{
	private static final long serialVersionUID = -2504630271232805552L;

	@NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String code;
}
