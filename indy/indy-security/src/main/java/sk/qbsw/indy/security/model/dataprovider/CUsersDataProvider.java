package sk.qbsw.indy.security.model.dataprovider;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.management.service.UserManagementService;

public class CUsersDataProvider implements IDataProvider<User>
{

	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserManagementService taskService;

	/**
	 * Stored data
	 */
	private List<User> users;

	private Group group;
	private Organization organization;
	private Boolean enabled;

	public CUsersDataProvider (Group group, Boolean enabled, Organization organization)
	{
		super();
		Injector.get().inject(this);
		this.group = group;
		this.organization = organization;
		this.enabled = enabled;

	}

	@Override
	public void detach ()
	{
		// Nothing to do
	}

	@Override
	public Iterator<? extends User> iterator(long first, long count)
	{
		return this.users.iterator();
	}

	@Override
	public IModel<User> model (User request)
	{
		return new Model<User>(request);
	}

	@Override
	public long size ()
	{
		this.users = taskService.getUsers(organization, enabled, group);

		return this.users.size();
	}

	public Group getGroup ()
	{
		return group;
	}

	public void setGroup (Group group)
	{
		this.group = group;
	}

	public Organization getOrganization ()
	{
		return organization;
	}

	public void setOrganization (Organization organization)
	{
		this.organization = organization;
	}

	public Boolean getEnabled ()
	{
		return enabled;
	}

	public void setEnabled (Boolean enabled)
	{
		this.enabled = enabled;
	}
	
	

}
