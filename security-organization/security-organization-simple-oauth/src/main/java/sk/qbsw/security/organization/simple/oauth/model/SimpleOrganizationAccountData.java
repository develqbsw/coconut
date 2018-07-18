package sk.qbsw.security.organization.simple.oauth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qbsw.core.security.base.model.AccountData;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * The account data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
@Setter
@NoArgsConstructor
public class SimpleOrganizationAccountData extends AccountData
{
	private static final long serialVersionUID = -6687794696674973074L;

	@NotNull
	private OrganizationData organization;

	/**
	 * Instantiates a new Simple organization account data.
	 *
	 * @param id the id
	 * @param login the login
	 * @param email the email
	 * @param roles the roles
	 * @param organization the organization
	 * @param additionalInformation the additional information
	 */
	public SimpleOrganizationAccountData (Long id, String login, String email, List<String> groups, List<String> roles, OrganizationData organization, Map<String, Object> additionalInformation)
	{
		super(id, login, email, groups, roles, additionalInformation);
		this.organization = organization;
	}
}
