package sk.qbsw.security.organization.complex.oauth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qbsw.core.security.base.model.AccountData;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * The complex organization account data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
@Setter
@NoArgsConstructor
public class ComplexOrganizationAccountData extends AccountData
{
	private static final long serialVersionUID = 286148328866315639L;

	@NotNull
	private List<OrganizationData> organizations;

	/**
	 * Instantiates a new Simple organization account data.
	 *
	 * @param id the id
	 * @param login the login
	 * @param email the email
	 * @param groups the groups
	 * @param roles the roles
	 * @param organizations the organizations
	 * @param additionalInformation the additional information
	 */
	public ComplexOrganizationAccountData (Long id, String login, String email, List<String> groups, List<String> roles, List<OrganizationData> organizations, Map<String, Object> additionalInformation)
	{
		super(id, login, email, groups, roles, additionalInformation);
		this.organizations = organizations;
	}
}
