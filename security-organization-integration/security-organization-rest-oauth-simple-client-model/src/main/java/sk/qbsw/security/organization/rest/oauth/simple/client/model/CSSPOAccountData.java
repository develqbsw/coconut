package sk.qbsw.security.organization.rest.oauth.simple.client.model;

import java.util.List;
import java.util.Map;

import lombok.*;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.CSAccountDataStates;

/**
 * The simple organization account data - use only in client implementation, do not use in controller (in controller, the userData is mapped by mapper).
 *
 * @author Tomas Lauro
 * @version 2.4.0
 * @since 2.2.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode (callSuper = true)
public class CSSPOAccountData extends CSAccountData
{
	private static final long serialVersionUID = 7790958331261348835L;

	private CSSPOUserData user;

	/**
	 * Instantiates a new Csspo account data.
	 *
	 * @param id the id
	 * @param uid the uid
	 * @param login the login
	 * @param email the email
	 * @param state the state
	 * @param roles the roles
	 * @param user the user
	 * @param additionalInformation the additional information
	 */
	public CSSPOAccountData (Long id, String uid, String login, String email, CSAccountDataStates state, List<String> roles, CSSPOUserData user, Map<String, Object> additionalInformation)
	{
		super(id, uid, login, email, state, roles, user, additionalInformation);
		this.user = user;
	}
}
