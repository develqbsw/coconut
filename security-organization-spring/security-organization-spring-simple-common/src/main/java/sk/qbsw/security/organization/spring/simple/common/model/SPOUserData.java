package sk.qbsw.security.organization.spring.simple.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qbsw.security.spring.common.model.UserData;

import javax.validation.constraints.NotNull;

/**
 * The simple organization user data.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
public class SPOUserData extends UserData
{
	private static final long serialVersionUID = -7673664220514309796L;

	@NotNull
	private SPOOrganization organization;

	/**
	 * Instantiates a new Spo user data.
	 *
	 * @param id the id
	 * @param organization the organization
	 */
	public SPOUserData (Long id, SPOOrganization organization)
	{
		super(id);
		this.organization = organization;
	}
}
