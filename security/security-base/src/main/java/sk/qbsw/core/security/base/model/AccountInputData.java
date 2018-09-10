package sk.qbsw.core.security.base.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

import static sk.qbsw.core.base.validation.ValidationGroup.Create;
import static sk.qbsw.core.base.validation.ValidationGroup.Update;

/**
 * The account input data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountInputData implements Serializable
{
	private static final long serialVersionUID = 7550450192096285816L;

	@NotNull (groups = {Update.class})
	private Long id;

	@NotNull (groups = {Create.class, Update.class})
	private String uid = UUID.randomUUID().toString();

	@NotNull (groups = {Create.class, Update.class})
	private String login;

	private String email;

	@NotNull (groups = {Create.class, Update.class})
	private AccountDataTypes type = AccountDataTypes.PERSONAL;

	// TODO: move to security-organization
	@NotNull (groups = {Create.class, Update.class})
	private Long organizationId;
}
