package sk.qbsw.core.security.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * The address for organizations users etc.
 * 
 * @author Michal Lacko
 * @version 1.7.2
 * @since 1.6.0
 */
@Entity
@Table (name = "t_address", schema = "sec")
public class CAddress extends ASecurityChangeEntity<Long>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6355326578030715757L;

	/** The id. */
	@Id
	@SequenceGenerator (name = "t_address_pkid_generator", sequenceName = "sec.t_address_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_address_pkid_generator")
	@Column (name = "pk_id")
	@Expose
	private Long id;

	/** The city. */
	@Column (name = "c_city", nullable = false)
	@Expose
	private String city;

	/** The house number. */
	@Column (name = "c_house_number", nullable = true)
	@Expose
	private String houseNumber;

	/** The state. */
	@Column (name = "c_state", nullable = false)
	@Expose
	private String state;

	/** The street. */
	@Column (name = "c_street", nullable = true)
	@Expose
	private String street;

	/** The zip code. */
	@Column (name = "c_zip_code", nullable = false)
	@Expose
	private String zipCode;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.model.domain.IEntity#getId()
	 */
	@Override
	public Long getId ()
	{
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId (Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity ()
	{
		return this.city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity (String city)
	{
		this.city = city;
	}


	/**
	 * Gets the house number.
	 *
	 * @return the houseNumber
	 */
	public String getHouseNumber ()
	{
		return houseNumber;
	}

	/**
	 * Sets the house number.
	 *
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber (String houseNumber)
	{
		this.houseNumber = houseNumber;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState ()
	{
		return this.state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState (String state)
	{
		this.state = state;
	}

	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet ()
	{
		return this.street;
	}

	/**
	 * Sets the street.
	 *
	 * @param street the new street
	 */
	public void setStreet (String street)
	{
		this.street = street;
	}

	/**
	 * Gets the zip code.
	 *
	 * @return the zip code
	 */
	public String getZipCode ()
	{
		return this.zipCode;
	}

	/**
	 * Sets the zip code.
	 *
	 * @param zipCode the new zip code
	 */
	public void setZipCode (String zipCode)
	{
		this.zipCode = zipCode;
	}

}
