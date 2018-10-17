package sk.qbsw.security.organization.rest.oauth.complex.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.BaseClientEntity;

import javax.validation.constraints.NotNull;

/**
 * The type Cs simplified unit.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSCXOUnitData extends BaseClientEntity
{
	private static final long serialVersionUID = -8801189653320932871L;

	@ApiModelProperty (required = true, value = "The identifier")
	@NotNull
	private Long id;

	@ApiModelProperty (value = "The unit name")
	@NotNull
	private String name;

	@ApiModelProperty (value = "The unit code")
	@NotNull
	private String code;
}
