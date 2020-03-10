package sk.qbsw.security.management.model;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qbsw.core.security.base.model.GroupOutputData;
import sk.qbsw.core.security.base.model.UserOutputData;

/**
 * The group manageable output data.
 *
 * @author Tomas Lauro
 * @version 2.5.0
 * @since 2.5.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupManageableOutputData extends GroupOutputData
{
	private static final long serialVersionUID = 8359580470377919970L;

	@NotNull
	private OffsetDateTime updated;

	@NotNull
	private UserOutputData updatedBy;
}
