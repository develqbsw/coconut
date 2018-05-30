package sk.qbsw.security.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qbsw.security.core.model.domain.Account;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The account data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.2
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountData implements Serializable
{
	private static final long serialVersionUID = -4837593660905770L;

	@NotNull
	private Long id;

	@NotNull
	private String login;

	private String email;

	@NotNull
	private List<String> roles;

	@NotNull
	private OrganizationData organization;

	@NotNull
	private Map<String, Object> additionalInformation = new HashMap<>();

	/**
	 * Create from account data.
	 *
	 * @param account the account
	 * @param additionalInformation the additional information
	 * @return the account data
	 */
	public static AccountData build (Account account, Map<String, Object> additionalInformation)
	{
		OrganizationData organizationData = new OrganizationData(account.getOrganization().getId(), account.getOrganization().getName(), account.getOrganization().getCode());
		if (additionalInformation != null)
		{
			return new AccountData(account.getId(), account.getLogin(), account.getEmail(), account.exportRoles(), organizationData, additionalInformation);
		}
		else
		{
			return new AccountData(account.getId(), account.getLogin(), account.getEmail(), account.exportRoles(), organizationData, new HashMap<>());
		}
	}
}
