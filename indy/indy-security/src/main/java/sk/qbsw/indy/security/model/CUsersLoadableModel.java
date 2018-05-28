package sk.qbsw.indy.security.model;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.management.service.UserManagementService;

public class CUsersLoadableModel extends LoadableDetachableModel<List<Account>>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserManagementService userService;

	private Organization organization;

private Role role;

	public CUsersLoadableModel (Organization organization, Role role)
	{
		super();

		this.organization = organization;
		this.role = role;

		Injector.get().inject(this);
	}

	@Override
	protected List<Account> load ()
	{
		List<Account> users;

		if (this.organization == null)
		{
			users = userService.getUsers();
		}
		else
		{
			users = userService.getUsers(organization, role);
		}
		return users;
	}
}
