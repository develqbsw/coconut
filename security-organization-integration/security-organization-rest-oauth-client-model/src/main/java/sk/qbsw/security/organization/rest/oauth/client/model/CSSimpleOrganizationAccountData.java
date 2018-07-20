package sk.qbsw.security.organization.rest.oauth.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;

import javax.validation.constraints.NotNull;

/**
 * The type Cs simple organization account data.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@EqualsAndHashCode (callSuper = true)
public class CSSimpleOrganizationAccountData extends CSAccountData
{
	private static final long serialVersionUID = -2986512153175808924L;

	@ApiModelProperty (required = true, value = "The organization")
	@NotNull
	private CSSimplifiedOrganization organization;
}
