package sk.qbsw.security.organization.complex.base.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qbsw.core.security.base.model.UserInputData;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static sk.qbsw.core.base.validation.ValidationGroup.Create;

/**
 * The complex organization user input data.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CXOUserInputData extends UserInputData
{
	private static final long serialVersionUID = 3630802904916410299L;

	@NotNull (groups = {Create.class})
	private List<CXOUserUnitInputData> units = new ArrayList<>();

	/**
	 * Instantiates a new Cxo user input data.
	 *
	 * @param id the id
	 * @param units the units
	 */
	public CXOUserInputData (Long id, List<CXOUserUnitInputData> units)
	{
		super(id);
		this.units = units;
	}
}
