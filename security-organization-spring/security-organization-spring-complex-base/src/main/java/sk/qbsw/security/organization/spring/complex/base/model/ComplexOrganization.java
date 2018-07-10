package sk.qbsw.security.organization.spring.complex.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The simplified organization.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplexOrganization implements Serializable
{
	private static final long serialVersionUID = 7635948004197182245L;

	@NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String code;

	private List<ComplexOrganizationUnit> units = new ArrayList<>();
}
