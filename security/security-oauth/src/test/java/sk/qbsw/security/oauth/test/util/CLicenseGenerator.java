package sk.qbsw.security.oauth.test.util;

import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.stereotype.Component;

import sk.qbsw.security.core.model.domain.CLicense;
import sk.qbsw.security.core.model.jmx.CLicensingRules;
import sk.qbsw.security.core.service.ILicenseGenerator;
import sk.qbsw.security.oauth.test.util.domain.CLicenseFree;

/**
 * License Generator.
 *
 * @author Lukas Podmajersky
 * 
 * @version 1.13.1
 * @since 1.13.1
 */
@Component ("oauthLicenseGenerator")
public class CLicenseGenerator implements ILicenseGenerator
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Generate free license.
	 *
	 * @param months the months
	 * @return the c license
	 */
	@Override
	public CLicense<CLicensingRules> generateFreeLicence (int months)
	{
		CLicense<CLicensingRules> licence = new CLicenseFree();

		Calendar validFrom = Calendar.getInstance();
		Calendar validTo = Calendar.getInstance();
		validTo.add(Calendar.MONTH, months);
		licence.setValidFrom(validFrom);
		licence.setValidTo(validTo);
		licence.setKey("");

		return licence;
	}

	/**
	 * Generate free license.
	 *
	 * @return the c licence
	 */
	public CLicense<?> generateOwnerLicence ()
	{
		CLicense<CLicensingRules> licence = new CLicenseFree();

		Calendar validTo = Calendar.getInstance();
		validTo.add(Calendar.YEAR, 100);
		licence.setValidTo(validTo);
		licence.setKey("");

		return licence;
	}

	/**
	 * Gets the available licenses.
	 *
	 * @return the available licenses
	 * @see sk.qbsw.core.security.service.ILicenseGenerator#getAvailableLicenses()
	 */
	@Override
	public ArrayList<CLicense<?>> getAvailableLicenses ()
	{
		ArrayList<CLicense<?>> retVal = new ArrayList<CLicense<?>>();
		retVal.add(new CLicenseFree());
		retVal.add(new CLicenseFree());

		return retVal;
	}

	/**
	 * Gets the available licenses for customer.
	 *
	 * @return the available licenses for customer
	 * @see sk.qbsw.core.security.service.ILicenseGenerator#getAvailableLicenses()
	 */
	@Override
	public ArrayList<CLicense<?>> getAvailableLicensesForCustomer ()
	{
		ArrayList<CLicense<?>> retVal = new ArrayList<CLicense<?>>();
		retVal.add(new CLicenseFree());
		retVal.add(new CLicenseFree());

		return retVal;
	}
}
