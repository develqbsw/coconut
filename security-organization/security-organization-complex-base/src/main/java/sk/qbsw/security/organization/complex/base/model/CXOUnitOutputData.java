package sk.qbsw.security.organization.complex.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The complex organization unit output data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CXOUnitOutputData implements Serializable
{
	private static final long serialVersionUID = 9066914762667020965L;

	@NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String code;
}
