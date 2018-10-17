package sk.qbsw.security.organization.rest.oauth.simple.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.security.rest.oauth.client.model.CSUserData;

import javax.validation.constraints.NotNull;

/**
 * The simple organization user data.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSSPOUserData extends CSUserData
{
	private static final long serialVersionUID = -743135861898539506L;

	@ApiModelProperty (required = true, value = "The organization")
	@NotNull
	private CSSPOOrganizationData organization;

	/**
	 * Instantiates a new Csspo user data.
	 *
	 * @param id the id
	 * @param organization the organization
	 */
	public CSSPOUserData (Long id, CSSPOOrganizationData organization)
	{
		super(id);
		this.organization = organization;
	}
}
