package sk.qbsw.core.security.base.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The group output data.
 *
 * @author Tomas Lauro
 * @version 2.5.0
 * @since 2.5.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupOutputData implements Serializable
{
	private static final long serialVersionUID = 5345835854104709123L;

	@NotNull
	private Long id;

	@NotNull
	private String code;

	@NotNull
	private GroupDataTypes type = GroupDataTypes.STANDARD;

	@NotNull
	private DataActivityStates state = DataActivityStates.ACTIVE;

	private String category;

	@NotNull
	private Set<RoleOutputData> roles = new HashSet<>();

	@NotNull
	private Set<GroupOutputData> excludedGroups = new HashSet<>();
}
