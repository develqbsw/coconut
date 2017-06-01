package sk.qbsw.security.authentication.test.util;

import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.stereotype.Component;

import sk.qbsw.security.authentication.test.util.domain.LicenseFree;
import sk.qbsw.security.authentication.test.util.domain.LicenseOwner;
import sk.qbsw.security.core.model.domain.CLicense;
import sk.qbsw.security.core.model.jmx.CLicensingRules;
import sk.qbsw.security.core.service.LicenseGenerator;

/**
 * License Generator.
 *
 * @author Lukas Podmajersky
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class TestLicenseGenerator implements LicenseGenerator
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
		CLicense<CLicensingRules> licence = new LicenseFree();

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
		CLicense<CLicensingRules> licence = new LicenseOwner();

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
	 * @see sk.qbsw.security.core.LicenseGenerator.service.ILicenseGenerator#getAvailableLicenses()
	 */
	@Override
	public ArrayList<CLicense<?>> getAvailableLicenses ()
	{
		ArrayList<CLicense<?>> retVal = new ArrayList<CLicense<?>>();
		retVal.add(new LicenseOwner());
		retVal.add(new LicenseFree());

		return retVal;
	}

	/**
	 * Gets the available licenses for customer.
	 *
	 * @return the available licenses for customer
	 * @see sk.qbsw.security.core.LicenseGenerator.service.ILicenseGenerator#getAvailableLicenses()
	 */
	@Override
	public ArrayList<CLicense<?>> getAvailableLicensesForCustomer ()
	{
		ArrayList<CLicense<?>> retVal = new ArrayList<CLicense<?>>();
		retVal.add(new LicenseOwner());
		retVal.add(new LicenseFree());

		return retVal;
	}
}
