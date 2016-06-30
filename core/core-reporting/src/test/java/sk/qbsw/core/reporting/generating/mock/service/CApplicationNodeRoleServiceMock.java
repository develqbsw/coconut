package sk.qbsw.core.reporting.generating.mock.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import sk.qbsw.core.reporting.generating.mock.model.CServerNodeRoleMock;
import sk.qbsw.core.reporting.generating.model.IServerNodeRoleEntity;
import sk.qbsw.core.reporting.generating.service.IApplicationServerNodeRoleService;

@Service
public class CApplicationNodeRoleServiceMock implements IApplicationServerNodeRoleService
{

	public static final String SERVER_NODE_NAME="testServer";
	public static final String SERVER_NODE_ROLE="testRole";
	private List<String>roles=new ArrayList<>();
	private List<CServerNodeRoleMock>nodeRoles=new ArrayList<>();
	
	@PostConstruct
	public void initialize(){
		roles=new ArrayList<>();
		roles.add(SERVER_NODE_ROLE);
		
		nodeRoles=new ArrayList<>();
		CServerNodeRoleMock nr1=new CServerNodeRoleMock();
		nr1.setServerRole(SERVER_NODE_ROLE);
		nr1.setThreadResources(2);
		nr1.setReportTypes("test1,test2");
		
		CServerNodeRoleMock nr2=new CServerNodeRoleMock();
		nr2.setServerRole(SERVER_NODE_ROLE);
		nr2.setThreadResources(2);
		nr2.setReportTypes("test3");
		nodeRoles.add(nr1);
		nodeRoles.add(nr2);
		
		roles.add(SERVER_NODE_ROLE);
		roles.add(SERVER_NODE_ROLE);
		
	}
	
	@Override
	public String getApplicationServerNodeName ()
	{
		return SERVER_NODE_NAME;
	}

	@Override
	public List<String> getApplicationServerNodeRoles ()
	{
		return roles;
	}

	@Override
	public List<? extends IServerNodeRoleEntity> getApplicationServerNodeRolesComplete ()
	{
		return nodeRoles;
	}

	@Override
	public boolean applicationServerNodeHasRole (String role)
	{
		for (String roleS : roles)
		{
			if(roleS.equals(role)){
				return true;
			}
		}
		return false;
	}

}
