package sk.qbsw.indy.security.model;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.service.IUserService;

public class CUsersLoadableModel extends LoadableDetachableModel<List<CUser>>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private IUserService userService;

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
