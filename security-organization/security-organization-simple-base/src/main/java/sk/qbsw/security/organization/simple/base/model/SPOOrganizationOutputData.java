package sk.qbsw.security.organization.simple.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The simple organization output data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SPOOrganizationOutputData implements Serializable
{
	private static final long serialVersionUID = 6368009909934050540L;

	@NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String code;
}
