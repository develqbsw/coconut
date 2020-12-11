package sk.qbsw.security.organization.rest.oauth.complex.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.core.client.model.BaseClientEntity;

/**
 * The simplified organization.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class CSCXOOrganizationData extends BaseClientEntity
{
	private static final long serialVersionUID = -9061103501380889630L;

	@Schema (required = true, description = "The identifier")
	@NotNull
	private Long id;

	@Schema (description = "The organization name")
	@NotNull
	private String name;

	@Schema (description = "The organization code")
	@NotNull
	private String code;

	@Schema (description = "the organization units")
	private List<CSCXOUnitData> units = new ArrayList<>();
}
