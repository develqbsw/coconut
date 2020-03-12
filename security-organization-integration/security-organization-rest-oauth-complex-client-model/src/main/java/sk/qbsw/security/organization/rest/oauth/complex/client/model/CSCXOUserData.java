package sk.qbsw.security.organization.rest.oauth.complex.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sk.qbsw.security.rest.oauth.client.model.CSUserData;

/**
 * The complex organization user data.
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
public class CSCXOUserData extends CSUserData
{
	private static final long serialVersionUID = 2118214771564519058L;

	@ApiModelProperty (required = true, value = "The organizations")
	@NotNull
	private List<CSCXOOrganizationData> organizations = new ArrayList<>();

	/**
	 * Instantiates a new Cscxo user data.
	 *
	 * @param id the id
	 * @param organizations the organizations
	 */
	public CSCXOUserData (Long id, List<CSCXOOrganizationData> organizations)
	{
		super(id);
		this.organizations = organizations;
	}
}
