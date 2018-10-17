package sk.qbsw.security.organization.complex.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static sk.qbsw.core.base.validation.ValidationGroup.Create;

/**
 * The complex organization unit input data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CXOUserUnitInputData implements Serializable
{
	private static final long serialVersionUID = 6211597272300296664L;

	@NotNull (groups = {Create.class})
	private String code;
}
