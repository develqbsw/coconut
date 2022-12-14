package sk.qbsw.core.configuration.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.persistence.converter.CEncryptionConverter;
import sk.qbsw.core.persistence.model.domain.AEntity;

/**
 * The system parameter.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.12.0
 * @since 1.8.0
 */
@Entity
@Table (name = "t_system_parameter", schema = "cfg")
public class CSystemParameter extends AEntity<Long> implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The entity id.
	 */
	@Id
	@SequenceGenerator (name = "t_system_parameter_pkid_generator", sequenceName = "cfg.t_system_parameter_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_system_parameter_pkid_generator")
	@Column (name = "pk_id", nullable = true)
	private Long id;

	/**
	 * The name of system parameter.
	 */
	@Column (name = "c_name", unique = true)
	private String name;

	/**
	 * The description of system parameter.
	 */
	@Column (name = "c_description")
	private String description;

	/**
	 * The date value of parameter.
	 */
	@Column (name = "c_date_value")
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	private OffsetDateTime dateValue;

	/**
	 * The integer value of parameter.
	 */
	@Column (name = "c_integer_value", precision = 16, scale = 0)
	private Integer integerValue;

	/**
	 * The string value of parameter.
	 */
	@Column (name = "c_string_value")
	private String stringValue;

	/**
	 * The float value of parameter.
	 */
	@Column (name = "c_float_value")
	private BigDecimal floatValue;

	/**
	 * The encrypted value of parameter.
	 */
	@Column (name = "c_encrypted_value")
	@Convert (converter = CEncryptionConverter.class)
	private String encryptedValue;

	/**
	 * Valid until.
	 */
	@Column (name = "c_valid_to_date")
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	private OffsetDateTime validToDate;

	/**
	 * Valid from.
	 */
	@Column (name = "c_valid_from_date")
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	private OffsetDateTime validFromDate;

	/**
	 * The module.
	 */
	@Column (name = "c_module")
	private String module;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.model.domain.IEntity#getId()
	 */
	@Override
	public Long getId ()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id
	 */
	public void setId (Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName ()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name
	 */
	public void setName (String name)
	{
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription ()
	{
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description
	 */
	public void setDescription (String description)
	{
		this.description = description;
	}

	/**
	 * Gets the date value.
	 *
	 * @return the date value
	 */
	public OffsetDateTime getDateValue ()
	{
		return dateValue;
	}

	/**
	 * Sets the date value.
	 *
	 * @param dateValue the date value
	 */
	public void setDateValue (OffsetDateTime dateValue)
	{
		this.dateValue = dateValue;
	}

	/**
	 * Gets the integer value.
	 *
	 * @return the integer value
	 */
	public Integer getIntegerValue ()
	{
		return integerValue;
	}

	/**
	 * Sets the integer value.
	 *
	 * @param integerValue the integer value
	 */
	public void setIntegerValue (Integer integerValue)
	{
		this.integerValue = integerValue;
	}

	/**
	 * Gets the string value.
	 *
	 * @return the string value
	 */
	public String getStringValue ()
	{
		return stringValue;
	}

	/**
	 * Sets the string value.
	 *
	 * @param stringValue the string value
	 */
	public void setStringValue (String stringValue)
	{
		this.stringValue = stringValue;
	}

	/**
	 * Gets the valid to date.
	 *
	 * @return the valid to date
	 */
	public OffsetDateTime getValidToDate ()
	{
		return validToDate;
	}

	/**
	 * Sets the valid to date.
	 *
	 * @param validToDate the valid to date
	 */
	public void setValidToDate (OffsetDateTime validToDate)
	{
		this.validToDate = validToDate;
	}

	/**
	 * Gets the valid from date.
	 *
	 * @return the valid from date
	 */
	public OffsetDateTime getValidFromDate ()
	{
		return validFromDate;
	}

	/**
	 * Sets the valid from date.
	 *
	 * @param validFromDate the valid from date
	 */
	public void setValidFromDate (OffsetDateTime validFromDate)
	{
		this.validFromDate = validFromDate;
	}

	/**
	 * Gets the module.
	 *
	 * @return the module
	 */
	public String getModule ()
	{
		return module;
	}

	/**
	 * Sets the module.
	 *
	 * @param module the module
	 */
	public void setModule (String module)
	{
		this.module = module;
	}

	/**
	 * @return the floatValue
	 */
	public BigDecimal getFloatValue ()
	{
		return floatValue;
	}

	/**
	 * @param floatValue the floatValue to set
	 */
	public void setFloatValue (BigDecimal floatValue)
	{
		this.floatValue = floatValue;
	}

	/**
	 * Gets the encrypted value.
	 *
	 * @return the encrypted value
	 */
	public String getEncryptedValue ()
	{
		return encryptedValue;
	}

	/**
	 * Sets the encrypted value.
	 *
	 * @param encryptedValue the new encrypted value
	 */
	public void setEncryptedValue (String encryptedValue)
	{
		this.encryptedValue = encryptedValue;
	}

	/**
	 * Checks if the parameter has at least one value.
	 */
	public void checkValue ()
	{
		if (dateValue == null && stringValue == null && integerValue == null)
		{
			throw new CSystemException("System parameter " + getName() + " has no value");
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{
		//in system parameters are saved passwords for system users and passwords for external API. For this case we do not want to log values in system parameters. For this, are logged only description data and id of system parameter.
		return "CSystemParameter [id=" + id + ", datumPlatnostiDo=" + validToDate + ", datumPlatnostiOd=" + validFromDate + ", hodnotaDate=<hidden>, hodnotaInt=<hidden>, hodnotaString=<hidden>, modul=" + module + ", nazov=" + name + ", popis=" + description + "]";
	}
}
