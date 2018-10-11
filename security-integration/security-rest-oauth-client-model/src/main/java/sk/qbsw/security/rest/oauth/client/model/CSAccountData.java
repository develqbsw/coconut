package sk.qbsw.security.rest.oauth.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sk.qbsw.core.client.model.BaseClientEntity;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The account data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSAccountData extends BaseClientEntity
{
	private static final long serialVersionUID = 7790958331261348835L;

	@ApiModelProperty (required = true, value = "The user id")
	@NotNull
	private Long id;

	@ApiModelProperty (required = true, value = "The user uid")
	@NotNull
	private String uid;

	@ApiModelProperty (required = true, value = "The login")
	@NotNull
	private String login;

	@ApiModelProperty (value = "The user email")
	private String email;

	@ApiModelProperty (required = true, value = "The user roles")
	@NotNull
	private List<String> roles;

	@ApiModelProperty (value = "The additional custom user information")
	private Map<String, Object> additionalInformation = new HashMap<>();
}
