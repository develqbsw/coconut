package sk.qbsw.security.organization.complex.base.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qbsw.core.security.base.model.UserOutputData;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * The complex organization user output data.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CXOUserOutputData extends UserOutputData
{
	private static final long serialVersionUID = 3630802904916410299L;

	@NotNull
	private List<CXOOrganizationOutputData> organizations = new ArrayList<>();

	/**
	 * Instantiates a new Complex organization user output data.
	 *
	 * @param id the id
	 * @param organizations the organizations
	 */
	public CXOUserOutputData (Long id, List<CXOOrganizationOutputData> organizations)
	{
		super(id);
		this.organizations = organizations;
	}
}
