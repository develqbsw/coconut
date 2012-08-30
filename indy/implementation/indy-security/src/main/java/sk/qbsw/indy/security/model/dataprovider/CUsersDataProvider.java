package sk.qbsw.indy.security.model.dataprovider;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.service.IUserService;

public class CUsersDataProvider implements IDataProvider<CUser>
{

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IUserService taskService;

	/**
	 * Stored data
	 */
	private List<CUser> users;

	private CGroup group;
	private COrganization organization;
	private Boolean enabled;

	public CUsersDataProvider (CGroup group, Boolean enabled, COrganization organization)
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
	public Iterator<? extends CUser> iterator (int arg0, int arg1)
	{
		return this.users.iterator();
	}

	@Override
	public IModel<CUser> model (CUser request)
	{
		return new Model<CUser>(request);
	}

	@Override
	public int size ()
	{
		this.users = taskService.getUsers(organization, enabled, group);

		return this.users.size();
	}

	public CGroup getGroup ()
	{
		return group;
	}

	public void setGroup (CGroup group)
	{
		this.group = group;
	}

	public COrganization getOrganization ()
	{
		return organization;
	}

	public void setOrganization (COrganization organization)
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
