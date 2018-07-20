package sk.qbsw.security.organization.rest.oauth.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * The type Cs simple organization account data.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSSimpleOrganizationAccountData extends CSAccountData
{
	private static final long serialVersionUID = -2986512153175808924L;

	@ApiModelProperty (required = true, value = "The organization")
	@NotNull
	private CSSimplifiedOrganization organization;

	/**
	 * Instantiates a new Cs simple organization account data.
	 *
	 * @param id the id
	 * @param login the login
	 * @param email the email
	 * @param roles the roles
	 * @param additionalInformation the additional information
	 * @param organization the organization
	 */
	public CSSimpleOrganizationAccountData (Long id, String login, String email, List<String> roles, Map<String, Object> additionalInformation, CSSimplifiedOrganization organization)
	{
		super(id, login, email, roles, additionalInformation);
		this.organization = organization;
	}
}
