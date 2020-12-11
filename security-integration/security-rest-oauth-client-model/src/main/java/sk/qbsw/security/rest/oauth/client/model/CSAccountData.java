package sk.qbsw.security.rest.oauth.client.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.BaseClientEntity;

/**
 * The account data.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSAccountData extends BaseClientEntity
{
	private static final long serialVersionUID = 7790958331261348835L;

	@Schema (required = true, description = "The user id")
	@NotNull
	private Long id;

	@Schema (required = true, description = "The user uid")
	@NotNull
	private String uid;

	@Schema (required = true, description = "The login")
	@NotNull
	private String login;

	@Schema (description = "The user email")
	private String email;

	@Schema (required = true, description = "The state")
	@NotNull
	private CSAccountDataStates state;

	@Schema (required = true, description = "The user roles")
	@NotNull
	private List<String> roles;

	private CSUserData user;

	@Schema (description = "The additional custom user information")
	private Map<String, Object> additionalInformation = new HashMap<>();
}
