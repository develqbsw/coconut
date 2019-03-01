package sk.qbsw.security.organization.spring.simple.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * The simplified organization.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.18.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SPOOrganization implements Serializable
{
	private static final long serialVersionUID = 3528029332180443692L;

	@NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String code;
}
