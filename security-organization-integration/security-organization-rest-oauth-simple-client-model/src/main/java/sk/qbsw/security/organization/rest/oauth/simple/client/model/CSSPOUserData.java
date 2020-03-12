package sk.qbsw.security.organization.rest.oauth.simple.client.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sk.qbsw.security.rest.oauth.client.model.CSUserData;

/**
 * The simple organization user data.
 *
 * @author Tomas Leken
 * @version 2.4.0
 * @since 2.0.0
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
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
