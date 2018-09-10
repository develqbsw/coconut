package sk.qbsw.security.organization.simple.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The simple organization data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleOrganizationData implements Serializable
{
	private static final long serialVersionUID = 6368009909934050540L;

	@NotNull
	private Long id;

	private String name;

	private String code;
}
