package sk.qbsw.security.organization.rest.oauth.complex.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.BaseClientEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * The simplified organization.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSCXOOrganizationData extends BaseClientEntity
{
	private static final long serialVersionUID = -9061103501380889630L;

	@ApiModelProperty (required = true, value = "The identifier")
	@NotNull
	private Long id;

	@ApiModelProperty (value = "The organization name")
	@NotNull
	private String name;

	@ApiModelProperty (value = "The organization code")
	@NotNull
	private String code;

	@ApiModelProperty (value = "the organization units")
	private List<CSCXOUnitData> units = new ArrayList<>();
}
