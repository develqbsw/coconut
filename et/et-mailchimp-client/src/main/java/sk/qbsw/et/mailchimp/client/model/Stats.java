package sk.qbsw.et.mailchimp.client.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The mailchimp stats.
 * 
 * @author Juraj Vrabec
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public class Stats extends BaseEntity
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5667166008120920013L;

	/** The avg open rate. */
	@ApiModelProperty (required = false, value = "A subscriber’s average open rate.")
	@JsonProperty (value = "avg_open_rate")
	private Double avgOpenRate;

	/** The avg click rate. */
	@ApiModelProperty (required = false, value = "A subscriber’s average clickthrough rate.")
	@JsonProperty (value = "avg_click_rate")
	private Double avgClickRate;

	/**
	 * Gets the avg open rate.
	 *
	 * @return the avg open rate
	 */
	public Double getAvgOpenRate ()
	{
		return avgOpenRate;
	}

	/**
	 * Sets the avg open rate.
	 *
	 * @param avgOpenRate the new avg open rate
	 */
	public void setAvgOpenRate (Double avgOpenRate)
	{
		this.avgOpenRate = avgOpenRate;
	}

	/**
	 * Gets the avg click rate.
	 *
	 * @return the avg click rate
	 */
	public Double getAvgClickRate ()
	{
		return avgClickRate;
	}

	/**
	 * Sets the avg click rate.
	 *
	 * @param avgClickRate the new avg click rate
	 */
	public void setAvgClickRate (Double avgClickRate)
	{
		this.avgClickRate = avgClickRate;
	}

	/* (non-Javadoc)
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
		Stats castOther = (Stats) other;
		return new EqualsBuilder().append(avgOpenRate, castOther.avgOpenRate).append(avgClickRate, castOther.avgClickRate).isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(-254727819, 171069457).append(avgOpenRate).append(avgClickRate).toHashCode();
	}
}
