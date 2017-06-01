package sk.qbsw.indy.security.model;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.management.service.GroupService;

public class CGroupsLoadableModel extends LoadableDetachableModel<List<Group>>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private GroupService groupService;

	public CGroupsLoadableModel ()
	{
		super();
		Injector.get().inject(this);
	}

	@Override
	protected List<Group> load ()
	{
		List<Group> groups = groupService.getAll();
		return groups;
	}
}
