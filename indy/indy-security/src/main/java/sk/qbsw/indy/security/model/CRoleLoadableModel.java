package sk.qbsw.indy.security.model;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import sk.qbsw.security.authorization.service.IAuthorizationService;
import sk.qbsw.security.core.model.domain.CRole;

public class CRoleLoadableModel extends LoadableDetachableModel<CRole>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private IAuthorizationService securityService;

	private String roleCode;

	public CRoleLoadableModel(String roleCode)
	{
		super();

		this.roleCode = roleCode;

		Injector.get().inject(this);
	}

	@Override
	protected CRole load()
	{
		List<CRole> roles = securityService.getRoleByCode(roleCode);

		return roles.get(0);
	}
}
