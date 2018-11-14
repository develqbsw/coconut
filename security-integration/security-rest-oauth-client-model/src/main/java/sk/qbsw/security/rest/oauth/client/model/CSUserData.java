package sk.qbsw.security.rest.oauth.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.BaseClientEntity;

import javax.validation.constraints.NotNull;

/**
 * The user data.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSUserData extends BaseClientEntity
{
	private static final long serialVersionUID = 7790958331261348835L;

	@ApiModelProperty (required = true, value = "The user id")
	@NotNull
	private Long id;
}
