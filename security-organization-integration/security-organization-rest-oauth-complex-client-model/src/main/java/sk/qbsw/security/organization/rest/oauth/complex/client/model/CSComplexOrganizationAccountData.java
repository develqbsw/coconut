package sk.qbsw.security.organization.rest.oauth.complex.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Cs complex organization account data.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSComplexOrganizationAccountData extends CSAccountData
{
	private static final long serialVersionUID = -9062410069413466963L;

	@ApiModelProperty (required = true, value = "The organizations")
	@NotNull
	private List<CSSimplifiedOrganization> organizations = new ArrayList<>();

	/**
	 * Instantiates a new Cs simple organization account data.
	 *
	 * @param id                    the id
	 * @param login                 the login
	 * @param email                 the email
	 * @param roles                 the roles
	 * @param additionalInformation the additional information
	 * @param organizations         the organizations
	 */
	public CSComplexOrganizationAccountData (Long id, String login, String email, List<String> roles, Map<String, Object> additionalInformation, List<CSSimplifiedOrganization> organizations)
	{
		super(id, login, email, roles, additionalInformation);
		this.organizations = organizations;
	}
}
