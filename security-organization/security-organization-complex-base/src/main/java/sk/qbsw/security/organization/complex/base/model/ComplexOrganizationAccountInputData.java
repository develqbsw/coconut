package sk.qbsw.security.organization.complex.base.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qbsw.core.security.base.model.AccountDataTypes;
import sk.qbsw.core.security.base.model.AccountInputData;

import javax.validation.constraints.NotNull;

import static sk.qbsw.core.base.validation.ValidationGroup.Create;
import static sk.qbsw.core.base.validation.ValidationGroup.Update;

/**
 * The complex organization account input data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
@Setter
@NoArgsConstructor
public class ComplexOrganizationAccountInputData extends AccountInputData
{
	private static final long serialVersionUID = 6401048600635452789L;

	@NotNull (groups = {Create.class, Update.class})
	private Long userId;

	/**
	 * Instantiates a new Complex organization account input data.
	 *
	 * @param id the id
	 * @param uid the uid
	 * @param login the login
	 * @param email the email
	 * @param type the type
	 * @param organizationId the organization id
	 * @param userId the user id
	 */
	public ComplexOrganizationAccountInputData (Long id, String uid, String login, String email, AccountDataTypes type, Long organizationId, Long userId)
	{
		super(id, uid, login, email, type, organizationId);
		this.userId = userId;
	}
}
