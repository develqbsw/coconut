package sk.qbsw.security.oauth.model;

import sk.qbsw.security.core.model.domain.User;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The account data.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class AccountData implements Serializable
{
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
	 * @param user the user
	 * @param additionalInformation the additional information
	 * @return the account data
	 */
	public static AccountData build (User user, Map<String, Object> additionalInformation)
	{
		OrganizationData organizationData = new OrganizationData(user.getOrganization().getId(), user.getOrganization().getName(), user.getOrganization().getCode());
		if (additionalInformation != null)
		{
			return new AccountData(user.getId(), user.getLogin(), user.getEmail(), user.exportRoles(), organizationData, additionalInformation);
		}
		else
		{
			return new AccountData(user.getId(), user.getLogin(), user.getEmail(), user.exportRoles(), organizationData, new HashMap<>());
		}
	}

	/**
	 * Instantiates a new Verification data.
	 */
	public AccountData ()
	{
	}

	/**
	 * Instantiates a new Account data.
	 *
	 * @param id the id
	 * @param login the login
	 * @param email the email
	 * @param roles the roles
	 * @param organization the organization
	 * @param additionalInformation the additional information
	 */
	public AccountData (Long id, String login, String email, List<String> roles, OrganizationData organization, Map<String, Object> additionalInformation)
	{
		this.id = id;
		this.login = login;
		this.email = email;
		this.roles = roles;
		this.organization = organization;
		this.additionalInformation = additionalInformation;
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public Long getId ()
	{
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId (Long id)
	{
		this.id = id;
	}

	/**
	 * Gets login.
	 *
	 * @return the login
	 */
	public String getLogin ()
	{
		return login;
	}

	/**
	 * Sets login.
	 *
	 * @param login the login
	 */
	public void setLogin (String login)
	{
		this.login = login;
	}

	/**
	 * Gets email.
	 *
	 * @return the email
	 */
	public String getEmail ()
	{
		return email;
	}

	/**
	 * Sets email.
	 *
	 * @param email the email
	 */
	public void setEmail (String email)
	{
		this.email = email;
	}

	/**
	 * Gets roles.
	 *
	 * @return the roles
	 */
	public List<String> getRoles ()
	{
		return roles;
	}

	/**
	 * Sets roles.
	 *
	 * @param roles the roles
	 */
	public void setRoles (List<String> roles)
	{
		this.roles = roles;
	}

	/**
	 * Gets organization.
	 *
	 * @return the organization
	 */
	public OrganizationData getOrganization ()
	{
		return organization;
	}

	/**
	 * Sets organization.
	 *
	 * @param organization the organization
	 */
	public void setOrganization (OrganizationData organization)
	{
		this.organization = organization;
	}

	/**
	 * Gets additional information.
	 *
	 * @return the additional information
	 */
	public Map<String, Object> getAdditionalInformation ()
	{
		return additionalInformation;
	}

	/**
	 * Sets additional information.
	 *
	 * @param additionalInformation the additional information
	 */
	public void setAdditionalInformation (Map<String, Object> additionalInformation)
	{
		this.additionalInformation = additionalInformation;
	}
}
