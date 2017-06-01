/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.authentication.test.util.domain;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import sk.qbsw.security.core.model.domain.License;
import sk.qbsw.security.core.model.jmx.CLicensingRules;

/**
 * The licence owner.
 *
 * @author Lukas Podmajersky
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("serial")
@Entity
@DiscriminatorValue ("owner")
public class LicenseOwner extends License<CLicensingRules>
{

	/**
	 * Instantiates a new c license free.
	 */
	public LicenseOwner ()
	{
		setType("owner");
		setKey("");
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 * @see sk.qbsw.security.core.core.model.domain.License#getPriority()
	 */
	@Override
	public Integer getPriority ()
	{
		return Integer.MAX_VALUE;
	}

	/**
	 * Validate license.
	 *
	 * @return true, if successful
	 * @see sk.qbsw.security.core.core.model.domain.License#validateLicense()
	 */
	public boolean validateLicense ()
	{
		return true;
	}

	/**
	 * Restrict export.
	 *
	 * @param rules
	 *            the rules
	 * @return the integer
	 * @see sk.qbsw.security.core.core.model.domain.License#restrictExport()
	 */
	@Override
	public Integer restrictExport (CLicensingRules rules)
	{
		return UNLIMITED;
	}

	/**
	 * Restrict respondents.
	 *
	 * @param rules
	 *            the rules
	 * @return the integer
	 * @see sk.qbsw.security.core.core.model.domain.License#restrictRespondents()
	 */
	@Override
	public Integer restrictRespondents (CLicensingRules rules)
	{
		return UNLIMITED;
	}

	/**
	 * Restrict questions.
	 *
	 * @param rules
	 *            the rules
	 * @return the integer
	 * @see sk.qbsw.security.core.core.model.domain.License#restrictQuestions()
	 */
	@Override
	public Integer restrictQuestions (CLicensingRules rules)
	{
		return UNLIMITED;
	}

	/**
	 * Restrict advanced functionality.
	 *
	 * @return true, if successful
	 * @see sk.qbsw.security.core.core.model.domain.License#isAllowedAdvancedFunctionality()
	 */
	@Override
	public boolean restrictAdvancedFunctionality ()
	{
		return true;
	}

	/**
	 * @see sk.qbsw.security.core.core.model.domain.License#recalculateLicensePrice(java.lang.Double)
	 */
	@Override
	public void recalculateLicensePrice (Double dayPrice)
	{
		setPrice(new BigDecimal(0d) );
	}

	/**
	 * Contact to get price.
	 *
	 * @return true, if successful
	 * @see sk.qbsw.security.core.core.model.domain.License#contactToGetPrice()
	 */
	@Override
	public boolean contactToGetPrice ()
	{
		return true;
	}

}
