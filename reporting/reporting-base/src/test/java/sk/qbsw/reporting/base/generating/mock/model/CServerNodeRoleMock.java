package sk.qbsw.reporting.base.generating.mock.model;

import sk.qbsw.reporting.base.generating.model.IServerNodeRoleEntity;

public class CServerNodeRoleMock implements IServerNodeRoleEntity
{

	private String serverRole;
	private String reportTypes;
	private Integer threadResources;
	public String getServerRole ()
	{
		return serverRole;
	}
	public void setServerRole (String serverRole)
	{
		this.serverRole = serverRole;
	}
	public String getReportTypes ()
	{
		return reportTypes;
	}
	public void setReportTypes (String reportTypes)
	{
		this.reportTypes = reportTypes;
	}
	public Integer getThreadResources ()
	{
		return threadResources;
	}
	public void setThreadResources (Integer threadResources)
	{
		this.threadResources = threadResources;
	}
	@Override
	public String getIdentificator ()
	{
		return "CServerNodeRoleMock test id";
	}
	@Override
	public String getServerName ()
	{
		return "test AS";
	}
	

}
