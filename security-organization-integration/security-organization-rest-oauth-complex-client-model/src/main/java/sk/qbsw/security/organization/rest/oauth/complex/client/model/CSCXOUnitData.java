package sk.qbsw.security.organization.rest.oauth.complex.client.model;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.BaseClientEntity;

/**
 * The type Cs simplified unit.
 *
 * @author Tomas Leken
 * @version 2.6.0
 * @since 2.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSCXOUnitData extends BaseClientEntity
{
	private static final long serialVersionUID = -8801189653320932871L;

	@Schema (required = true, description = "The identifier")
	@NotNull
	private Long id;

	@Schema (description = "The unit name")
	@NotNull
	private String name;

	@Schema (description = "The unit code")
	@NotNull
	private String code;
}
