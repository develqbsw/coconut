package sk.qbsw.security.organization.rest.oauth.complex.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.security.rest.oauth.client.model.CSUserData;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * The complex organization user data.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
@Data
@NoArgsConstructor
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
