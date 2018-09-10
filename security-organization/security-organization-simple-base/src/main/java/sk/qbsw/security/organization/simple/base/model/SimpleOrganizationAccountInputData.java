package sk.qbsw.security.organization.simple.base.model;

import lombok.*;
import sk.qbsw.core.security.base.model.AccountDataTypes;
import sk.qbsw.core.security.base.model.AccountInputData;

/**
 * The simple organization account input data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
@Setter
@NoArgsConstructor
public class SimpleOrganizationAccountInputData extends AccountInputData
{
	private static final long serialVersionUID = 416367468144681595L;

	/**
	 * Instantiates a new Simple organization account input data.
	 *
	 * @param id the id
	 * @param uid the uid
	 * @param login the login
	 * @param email the email
	 * @param type the type
	 * @param organizationId the organization id
	 */
	public SimpleOrganizationAccountInputData (Long id, String uid, String login, String email, AccountDataTypes type, Long organizationId)
	{
		super(id, uid, login, email, type, organizationId);
	}
}
