package sk.qbsw.security.organization.simple.base.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qbsw.core.security.base.model.UserOutputData;

import javax.validation.constraints.NotNull;

/**
 * The simple organization user output data.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
public class SPOUserOutputData extends UserOutputData
{
	private static final long serialVersionUID = 8760023429881645949L;

	@NotNull
	private SPOOrganizationOutputData organization;

	/**
	 * Instantiates a new Spo user output data.
	 *
	 * @param id the id
	 * @param organization the organization
	 */
	public SPOUserOutputData (Long id, SPOOrganizationOutputData organization)
	{
		super(id);
		this.organization = organization;
	}
}
