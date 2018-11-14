package sk.qbsw.security.organization.complex.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The complex organization output data.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CXOOrganizationOutputData implements Serializable
{
	private static final long serialVersionUID = 5084570889836969457L;

	@NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String code;

	private List<CXOUnitOutputData> units = new ArrayList<>();
}
