/**
 * 
 */
package sk.qbsw.core.security.service;

import java.io.Serializable;
import java.util.ArrayList;

import sk.qbsw.core.security.model.domain.CLicense;

/**
 * The Interface ILicenseGenerator.
 *
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 */
public interface ILicenseGenerator extends Serializable
{

	/**
	 * Gets available licencing rules.
	 *
	 * @return the available licenses
	 */
	public abstract ArrayList<CLicense<?>> getAvailableLicenses ();

	/**
	 * Generate free licence.
	 *
	 * @param months the months
	 * @return the c license
	 */
	public CLicense<?> generateFreeLicence (int months);

	/**
	 * Gets the available licenses for customer.
	 *
	 * @return the available licenses for customer
	 */
	public ArrayList<CLicense<?>> getAvailableLicensesForCustomer ();

}
