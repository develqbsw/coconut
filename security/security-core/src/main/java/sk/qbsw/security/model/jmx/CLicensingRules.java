/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.model.jmx;

import java.io.Serializable;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.service.AService;

/**
 * The Class CRelease.
 *
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 */
@Service("licensingRules")
@SuppressWarnings ("serial")
@ManagedResource (objectName = "sk.qbsw.airlines:name=Licensing", description = "Licensing details")
public class CLicensingRules extends AService implements Serializable
{

	/** The day pricing. */
	private Double dayPricing = 1d;

	/** The tax percentage. */
	private Float taxPercentage = 20f;

	/** The restricted export. */
	private Integer restrictedExport = 600;

	/** The restricted questions. */
	private Integer restrictedQuestions = 20;

	/** The restricted respondents. */
	private Integer restrictedRespondents = 200;

	/**
	 * Gets the day pricing.
	 *
	 * @return the day pricing
	 */
	public Double getDayPricing ()
	{
		return dayPricing;
	}

	/**
	 * Gets the restricted export.
	 *
	 * @return the restricted export
	 */
	public Integer getRestrictedExport ()
	{
		return restrictedExport;
	}

	/**
	 * Gets the restricted questions.
	 *
	 * @return the restricted questions
	 */
	public Integer getRestrictedQuestions ()
	{
		return restrictedQuestions;
	}

	/**
	 * Gets the restricted respondents.
	 *
	 * @return the restricted respondents
	 */
	public Integer getRestrictedRespondents ()
	{
		return restrictedRespondents;
	}

	/**
	 * Sets the restricted respondents.
	 *
	 * @param restrictedRespondents the new restricted respondents
	 */
	public void setRestrictedRespondents (Integer restrictedRespondents)
	{
		this.restrictedRespondents = restrictedRespondents;
	}

	/**
	 * Sets the day pricing.
	 *
	 * @param dayPricing the new day pricing
	 */
	public void setDayPricing (Double dayPricing)
	{
		this.dayPricing = dayPricing;
	}

	/**
	 * Sets the restricted export.
	 *
	 * @param restrictedExport the new restricted export
	 */
	public void setRestrictedExport (Integer restrictedExport)
	{
		this.restrictedExport = restrictedExport;
	}

	/**
	 * Sets the restricted questions.
	 *
	 * @param restrictedQuestions the new restricted questions
	 */
	public void setRestrictedQuestions (Integer restrictedQuestions)
	{
		this.restrictedQuestions = restrictedQuestions;
	}

	/**
	 * Gets the tax percentage.
	 *
	 * @return the tax percentage
	 */
	public Float getTaxPercentage ()
	{
		return taxPercentage;
	}

	/**
	 * Sets the tax percentage.
	 *
	 * @param taxPercentage the new tax percentage
	 */
	public void setTaxPercentage (Float taxPercentage)
	{
		this.taxPercentage = taxPercentage;
	}



}
