package sk.qbsw.reporting.base.generating.service;

import java.util.List;

import sk.qbsw.reporting.base.generating.model.IServerNodeRoleEntity;

/**
 * interface for application server node roles
 * @author Peter Bozik
 *
 */
public interface IApplicationServerNodeRoleService  {

	/**
	 * 
	 * @return application server node name
	 */
	public String getApplicationServerNodeName ();

	/**
	 * 
	 * @return all roles for this server
	 */
	public List<String> getApplicationServerNodeRoles ();

	/**
	 * 
	 * @return {@link List} of {@link IServerNodeRoleEntity} for this application server
	 */
	public List<? extends IServerNodeRoleEntity> getApplicationServerNodeRolesComplete ();

	/**
	 * Verifies if this server has specified role
	 * @param role
	 * @return
	 */
	public boolean applicationServerNodeHasRole (String role);

}
