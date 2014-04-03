/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.code.security.test.util.domain;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import sk.qbsw.core.security.model.domain.CLicense;
import sk.qbsw.core.security.model.jmx.CLicensingRules;

/**
 * The licence owner.
 *
 * @author Lukas Podmajersky
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue ("owner")
public class CLicenseOwner extends CLicense<CLicensingRules>
{

	/**
	 * Instantiates a new c license free.
	 */
	public CLicenseOwner ()
	{
		setType("owner");
		setKey("");
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 * @see sk.qbsw.core.security.model.domain.CLicense#getPriority()
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
	 * @see sk.qbsw.core.security.model.domain.CLicense#validateLicense()
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
	 * @see sk.qbsw.core.security.model.domain.CLicense#restrictExport()
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
	 * @see sk.qbsw.core.security.model.domain.CLicense#restrictRespondents()
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
	 * @see sk.qbsw.core.security.model.domain.CLicense#restrictQuestions()
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
	 * @see sk.qbsw.core.security.model.domain.CLicense#isAllowedAdvancedFunctionality()
	 */
	@Override
	public boolean restrictAdvancedFunctionality ()
	{
		return true;
	}

	/**
	 * @see sk.qbsw.core.security.model.domain.CLicense#recalculateLicensePrice(java.lang.Double)
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
	 * @see sk.qbsw.core.security.model.domain.CLicense#contactToGetPrice()
	 */
	@Override
	public boolean contactToGetPrice ()
	{
		return true;
	}

}
