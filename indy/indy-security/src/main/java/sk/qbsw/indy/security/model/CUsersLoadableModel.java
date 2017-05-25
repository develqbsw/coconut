package sk.qbsw.indy.security.model;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sk.qbsw.security.management.service.IUserManagementService;
import sk.qbsw.security.model.domain.COrganization;
import sk.qbsw.security.model.domain.CRole;
import sk.qbsw.security.model.domain.CUser;

public class CUsersLoadableModel extends LoadableDetachableModel<List<CUser>>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private IUserManagementService userService;

	private COrganization organization;

private CRole role;

	public CUsersLoadableModel (COrganization organization, CRole role)
	{
		super();

		this.organization = organization;
		this.role = role;

		Injector.get().inject(this);
	}

	@Override
	protected List<CUser> load ()
	{
		List<CUser> users;

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
