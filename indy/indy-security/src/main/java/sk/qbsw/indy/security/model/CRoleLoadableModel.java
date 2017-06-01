package sk.qbsw.indy.security.model;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sk.qbsw.security.authorization.service.AuthorizationService;
import sk.qbsw.security.core.model.domain.Role;

public class CRoleLoadableModel extends LoadableDetachableModel<Role>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private AuthorizationService securityService;

	private String roleCode;

	public CRoleLoadableModel(String roleCode)
	{
		super();

		this.roleCode = roleCode;

		Injector.get().inject(this);
	}

	@Override
	protected Role load()
	{
		List<Role> roles = securityService.getRoleByCode(roleCode);

		return roles.get(0);
	}
}
