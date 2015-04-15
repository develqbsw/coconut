/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The Class CLicence.
 *
 * @param <T> the generic type
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.6.1
 * @since 1.0
 */
@Entity
@Table (name = "t_licence", schema = "sec")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "d_type", discriminatorType = DiscriminatorType.STRING)
public abstract class CLicense<T> extends ASecurityChangeEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6005150237733706948L;

	/** The UNLIMITED. */
	public final static Integer UNLIMITED = -1;

	/** The flag payed. */
	@Column (name = "c_flag_payed")
	private Boolean flagPayed;

	/** The tax id of a subject which has payed for a license. */
	@Column (name = "c_tax_id")
	private String taxId;

	/** The key to activate a licence. */
	@Column (name = "c_key")
	private String key;

	//bi-directional many-to-one association to COrganization
	/** The organization. */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_organization")
	private COrganization organization;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "t_licence_pkid_generator", sequenceName = "sec.t_licence_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_licence_pkid_generator")
	@Column (name = "pk_id")
	private Long id;

	/** The price. */
	@Column (name = "c_price")
	private BigDecimal price;

	/** Type of the licence. */
	@Column (name = "d_type", insertable = false, updatable = false)
	private String type;

	/** The valid to. */
	@Column (name = "c_valid_from")
	private Calendar validFrom;

	/** The valid to. */
	@Column (name = "c_valid_to")
	private Calendar validTo;

	/**
	 * Instantiates a new c licence.
	 */
	public CLicense ()
	{
		flagPayed = false;
	}

	/**
	 * Gets the count export.
	 *
	 * @param parameters the parameters
	 * @return the count export
	 */
	public abstract Integer restrictExport (T parameters);


	/**
	 * Gets the count respondents.
	 *
	 * @param parameters the parameters
	 * @return the count respondents
	 */
	public abstract Integer restrictRespondents (T parameters);



	/**
	 * Gets the count surveys.
	 *
	 * @param parameters the parameters
	 * @return the count surveys
	 */
	public abstract Integer restrictQuestions (T parameters);

	/**
	 * Checks if is allowed advanced functionality.
	 *
	 * @return the boolean
	 */
	public abstract boolean restrictAdvancedFunctionality ();

	/**
	 * Contact to get price.
	 *
	 * @return true, if successful
	 */
	public abstract boolean contactToGetPrice ();


	/**
	 * Gets the flag payed.
	 *
	 * @return the flag payed
	 */
	public Boolean getFlagPayed ()
	{
		return flagPayed;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey ()
	{
		return this.key;
	}

	/**
	 * Gets the organization.
	 *
	 * @return the organization
	 */
	public COrganization getOrganization ()
	{
		return this.organization;
	}

	/**
	 * Gets the pk id.
	 *
	 * @return the pk id
	 */
	public Long getId ()
	{
		return this.id;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public BigDecimal getPrice ()
	{
		return price;
	}

	/**
	 * Gets the priority of license.
	 *
	 * @return the priority
	 */
	@Transient
	public abstract Integer getPriority ();

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType ()
	{
		return type;
	}


	/**
	 * Gets the valid from.
	 *
	 * @return the valid from
	 */
	public Calendar getValidFrom ()
	{
		return validFrom;
	}

	/**
	 * Gets the valid to.
	 *
	 * @return the valid to
	 */
	public Calendar getValidTo ()
	{
		return this.validTo;
	}

	/**
	 * Checks if is valid for actual date.
	 *
	 * @return the boolean
	 */
	protected Boolean isValidForActualDate ()
	{
		Calendar now = Calendar.getInstance();
		return (now.before(getValidTo()) && now.after(getValidFrom()));
	}

	/**
	 * Sets the flag payed.
	 *
	 * @param flagPayed the new flag payed
	 */
	public void setFlagPayed (Boolean flagPayed)
	{
		this.flagPayed = flagPayed;
	}

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey (String key)
	{
		this.key = key;
	}

	/**
	 * Sets the organization.
	 *
	 * @param organization the new organization
	 */
	public void setOrganization (COrganization organization)
	{
		this.organization = organization;
	}

	/**
	 * Sets the pk id.
	 *
	 * @param id the new pk id
	 */
	public void setId (Long id)
	{
		this.id = id;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice (BigDecimal price)
	{
		this.price = price;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType (String type)
	{
		this.type = type;
	}

	/**
	 * Sets the valid from.
	 *
	 * @param validFrom the new valid from
	 */
	public void setValidFrom (Calendar validFrom)
	{
		this.validFrom = validFrom;
	}

	/**
	 * Sets the valid to.
	 *
	 * @param validTo the new valid to
	 */
	public void setValidTo (Calendar validTo)
	{
		this.validTo = validTo;
	}

	/**
	 * Validate license.
	 *
	 * @return true, if successful
	 */
	public boolean validateLicense ()
	{
		return true;
	}

	public void recalculateLicensePrice (Double dayPrice)
	{
		long days = (validTo.getTimeInMillis() - validFrom.getTimeInMillis()) / (1000 * 60 * 60 * 24);

		int months = (int) days / 30;
		if (days % 30 > 0)
		{
			months += 1;
		}

		Double value = months * dayPrice * 30;
		if (value < 0)
		{
			value = 0d;
		}

		double result = value * 100;
		result = (int) (result);
		this.price = new BigDecimal(result / 100);
	}

	/**
	 * Gets the tax id.
	 *
	 * @return the tax id
	 */
	public String getTaxId ()
	{
		return taxId;
	}

	/**
	 * Sets the tax id.
	 *
	 * @param taxId the new tax id
	 */
	public void setTaxId (String taxId)
	{
		this.taxId = taxId;
	}

	/**
	 * Checks for tax id.
	 *
	 * @return true, if successful
	 */
	public boolean hasTaxId ()
	{
		return taxId != null && taxId.length() > 0;
	}
}
