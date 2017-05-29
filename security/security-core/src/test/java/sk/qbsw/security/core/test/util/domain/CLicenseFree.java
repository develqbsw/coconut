/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.test.util.domain;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import sk.qbsw.security.core.model.domain.CLicense;
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
public class CLicenseFree extends CLicense<CLicensingRules>
{
	/**
	 * Instantiates a new c license free.
	 */
	public CLicenseFree ()
	{
		setType("free");
		setKey("");
		setPrice(BigDecimal.ZERO);
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 * @see sk.qbsw.security.core.core.model.domain.CLicense#getPriority()
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
	 * @see sk.qbsw.security.core.core.model.domain.CLicense#validateLicense()
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
	 * @see sk.qbsw.security.core.core.model.domain.CLicense#restrictExport()
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
	 * @see sk.qbsw.security.core.core.model.domain.CLicense#restrictRespondents()
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
	 * @see sk.qbsw.security.core.core.model.domain.CLicense#restrictQuestions()
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
	 * @see sk.qbsw.security.core.core.model.domain.CLicense#isAllowedAdvancedFunctionality()
	 */
	@Override
	public boolean restrictAdvancedFunctionality ()
	{
		return false;
	}

	/**
	 * @see sk.qbsw.security.core.core.model.domain.CLicense#recalculateLicensePrice(java.lang.Double)
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
	 * @see sk.qbsw.security.core.core.model.domain.CLicense#contactToGetPrice()
	 */
	@Override
	public boolean contactToGetPrice ()
	{
		return false;
	}
}
