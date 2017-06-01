/**
 * 
 */
package sk.qbsw.security.core.service;

import java.io.Serializable;
import java.util.ArrayList;

import sk.qbsw.security.core.model.domain.License;

/**
 * The Interface LicenseGenerator.
 *
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 */
public interface LicenseGenerator extends Serializable
{

	/**
	 * Gets available licencing rules.
	 *
	 * @return the available licenses
	 */
	public abstract ArrayList<License<?>> getAvailableLicenses ();

	/**
	 * Generate free licence.
	 *
	 * @param months the months
	 * @return the c license
	 */
	public License<?> generateFreeLicence (int months);

	/**
	 * Gets the available licenses for customer.
	 *
	 * @return the available licenses for customer
	 */
	public ArrayList<License<?>> getAvailableLicensesForCustomer ();

}
