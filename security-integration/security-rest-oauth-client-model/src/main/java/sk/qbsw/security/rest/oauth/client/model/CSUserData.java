package sk.qbsw.security.rest.oauth.client.model;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.BaseClientEntity;

/**
 * The user data.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSUserData extends BaseClientEntity
{
	private static final long serialVersionUID = 7790958331261348835L;

	@Schema (required = true, description = "The user id")
	@NotNull
	private Long id;
}
