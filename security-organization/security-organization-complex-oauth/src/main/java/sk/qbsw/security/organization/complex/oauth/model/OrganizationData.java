package sk.qbsw.security.organization.complex.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The complex organization data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationData implements Serializable
{
	private static final long serialVersionUID = 5084570889836969457L;

	@NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String code;

	private List<UnitData> units = new ArrayList<>();
}
