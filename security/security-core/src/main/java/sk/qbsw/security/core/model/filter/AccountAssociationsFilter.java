package sk.qbsw.security.core.model.filter;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.Role;

/**
 * The account associations filter.
 * 
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.0
 */
@Getter
@Setter
public class AccountAssociationsFilter
{
	private Organization organization;

	private ActivityStates state;

	private Group group;

	private Role role;

	private Account excludedAccount;
}
