/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.test.util.domain;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import sk.qbsw.security.core.model.domain.License;
import sk.qbsw.security.core.model.jmx.CLicensingRules;

/**
 * The free licence.
 *
 * @author Lukas Podmajersky
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("serial")
@Entity
@DiscriminatorValue ("free")
public class LicenseFree extends License<CLicensingRules>
{
	/**
	 * Instantiates a new c license free.
	 */
	public LicenseFree ()
	{
		setType("free");
		setKey("");
		setPrice(BigDecimal.ZERO);
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
		return 1;
	}

	/**
	 * Validate license.
	 *
	 * @return true, if successful
	 * @see sk.qbsw.security.core.core.model.domain.License#validateLicense()
	 */
	@Override
	public boolean validateLicense ()
	{
		return isValidForActualDate();
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
		return rules.getRestrictedExport();
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
		return rules.getRestrictedQuestions();
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
		return false;
	}

	/**
	 * @see sk.qbsw.security.core.core.model.domain.License#recalculateLicensePrice(java.lang.Double)
	 */
	@Override
	public void recalculateLicensePrice (Double dayPrice)
	{
		setPrice(new BigDecimal(0d));
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
		return false;
	}
}
