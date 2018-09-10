package sk.qbsw.security.organization.simple.base.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountDataTypes;

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
	private SimpleOrganizationData organization;

	/**
	 * Instantiates a new Simple organization account data.
	 *
	 * @param id the id
	 * @param uid the uid
	 * @param login the login
	 * @param email the email
	 * @param type the type
	 * @param groups the groups
	 * @param roles the roles
	 * @param organization the organization
	 * @param additionalInformation the additional information
	 */
	public SimpleOrganizationAccountData (Long id, String uid, String login, String email, AccountDataTypes type, List<String> groups, List<String> roles, SimpleOrganizationData organization, Map<String, Object> additionalInformation)
	{
		super(id, uid, login, email, type, groups, roles, additionalInformation);
		this.organization = organization;
	}
}
