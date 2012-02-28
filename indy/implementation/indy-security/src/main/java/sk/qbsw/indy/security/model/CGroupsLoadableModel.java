package sk.qbsw.indy.security.model;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.service.IGroupService;

public class CGroupsLoadableModel extends LoadableDetachableModel<List<CGroup>>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private IGroupService groupService;

	public CGroupsLoadableModel ()
	{
		super();
		Injector.get().inject(this);
	}

	@Override
	protected List<CGroup> load ()
	{
		List<CGroup> groups = groupService.getAll();
		return groups;
	}
}
