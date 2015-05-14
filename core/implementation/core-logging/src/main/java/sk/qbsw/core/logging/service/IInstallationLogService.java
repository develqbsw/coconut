package sk.qbsw.core.logging.service;

import java.util.List;

/**
 * The installation log service.
 * 
 * @author Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
public interface IInstallationLogService
{
	/**
	 * Checks if the specified patch version was installed. 
	 *
	 * @param version the version
	 * @return true, if successful
	 */
	public boolean checkVersion (String version);

	/**
	 * Checks if the specified patch versions were installed.
	 *
	 * @param versions the versions
	 * @return true, if successful
	 * @throws IllegalArgumentException if the version list is null or empty
	 */
	public boolean checkVersionList (List<String> versions);
}
