package sk.qbsw.security.organization.rest.oauth.complex.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;

import java.util.List;
import java.util.Map;

/**
 * The complex organization account data - use only in client implementation, do not use in controller (in controller, the userData is mapped by mapper).
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSCXOAccountData extends CSAccountData
{
	private static final long serialVersionUID = -7233175392953773456L;

	private CSCXOUserData user;

	/**
	 * Instantiates a new Cscxo account data.
	 *
	 * @param id the id
	 * @param uid the uid
	 * @param login the login
	 * @param email the email
	 * @param roles the roles
	 * @param user the user
	 * @param additionalInformation the additional information
	 */
	public CSCXOAccountData (Long id, String uid, String login, String email, List<String> roles, CSCXOUserData user, Map<String, Object> additionalInformation)
	{
		super(id, uid, login, email, roles, user, additionalInformation);
		this.user = user;
	}
}
