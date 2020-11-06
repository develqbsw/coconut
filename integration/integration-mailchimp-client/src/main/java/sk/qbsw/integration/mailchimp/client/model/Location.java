package sk.qbsw.integration.mailchimp.client.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The mailchimp location.
 * 
 * @author Juraj Vrabec
 * @version 2.6.0
 * @since 1.17.0
 */
public class Location extends BaseEntity
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2067624274179186536L;

	/** The location latitude. */
	@Schema (description = "The location latitude.")
	private Double latitude;

	/** The location longitude. */
	@Schema (description = "The location longitude.")
	private Double longitude;

	/** The time difference in hours from GMT. */
	@Schema (description = "The time difference in hours from GMT.")
	private Integer gmtoff;

	/** The offset for timezones where daylight saving time is observed. */
	@Schema (description = "The offset for timezones where daylight saving time is observed.")
	private Integer dstoff;

	/** The unique code for the location country. */
	@Schema (description = "The unique code for the location country.")
	@JsonProperty (value = "country_code")
	private String countryCode;

	/** The timezone for the location.. */
	@Schema (description = "The timezone for the location.")
	private String timezone;

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public Double getLatitude ()
	{
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLatitude (Double latitude)
	{
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public Double getLongitude ()
	{
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new longitude
	 */
	public void setLongitude (Double longitude)
	{
		this.longitude = longitude;
	}

	/**
	 * Gets the gmtoff.
	 *
	 * @return the gmtoff
	 */
	public Integer getGmtoff ()
	{
		return gmtoff;
	}

	/**
	 * Sets the gmtoff.
	 *
	 * @param gmtoff the new gmtoff
	 */
	public void setGmtoff (Integer gmtoff)
	{
		this.gmtoff = gmtoff;
	}

	/**
	 * Gets the dstoff.
	 *
	 * @return the dstoff
	 */
	public Integer getDstoff ()
	{
		return dstoff;
	}

	/**
	 * Sets the dstoff.
	 *
	 * @param dstoff the new dstoff
	 */
	public void setDstoff (Integer dstoff)
	{
		this.dstoff = dstoff;
	}

	/**
	 * Gets the country code.
	 *
	 * @return the country code
	 */
	public String getCountryCode ()
	{
		return countryCode;
	}

	/**
	 * Sets the country code.
	 *
	 * @param countryCode the new country code
	 */
	public void setCountryCode (String countryCode)
	{
		this.countryCode = countryCode;
	}

	/**
	 * Gets the timezone.
	 *
	 * @return the timezone
	 */
	public String getTimezone ()
	{
		return timezone;
	}

	/**
	 * Sets the timezone.
	 *
	 * @param timezone the new timezone
	 */
	public void setTimezone (String timezone)
	{
		this.timezone = timezone;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (final Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (other == null)
		{
			return false;
		}
		if (!getClass().equals(other.getClass()))
		{
			return false;
		}
		Location castOther = (Location) other;
		return new EqualsBuilder().append(latitude, castOther.latitude).append(longitude, castOther.longitude).append(gmtoff, castOther.gmtoff).append(dstoff, castOther.dstoff).append(countryCode, castOther.countryCode).append(timezone, castOther.timezone).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(-1817253931, -938273395).append(latitude).append(longitude).append(gmtoff).append(dstoff).append(countryCode).append(timezone).toHashCode();
	}
}
