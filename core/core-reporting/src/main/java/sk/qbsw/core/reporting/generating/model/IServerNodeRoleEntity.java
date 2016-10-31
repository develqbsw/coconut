package sk.qbsw.core.reporting.generating.model;

/**
 * Interface for node role entity
 * @author Peter Bozik
 *
 */
public interface IServerNodeRoleEntity
{
	/**
	 * 
	 * @return role of the server
	 */
	public String getServerRole();
	
	/**
	 * 
	 * @return name of the server
	 */
	public String getServerName();
	/**
	 * 
	 * @return
	 */
	public String getReportTypes();
	/**
	 * 
	 * @return identificator of the role entity (e.g. Long id)
	 */
	public String getIdentificator();
	/**
	 * 
	 * @return count of the resources to be used
	 */
	public Integer getThreadResources();
}
