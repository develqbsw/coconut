package sk.qbsw.integration.mailchimp.client.model.request;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import sk.qbsw.integration.mailchimp.client.model.BaseEntity;
import sk.qbsw.integration.mailchimp.client.model.Location;

/**
 * The mailchimp add list member request body.
 *
 * @author Juraj Vrabec
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public class MailchimpAddListMemberRequestBody extends BaseEntity
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2447928182404463523L;

	/** Email address for a subscriber. */
	@ApiModelProperty (required = true, value = "Email address for a subscriber.")
	@NotNull
	@JsonProperty (value = "email_address")
	private String emailAddress;

	/** Type of email this member asked to get (‘html’ or ‘text’). */
	@ApiModelProperty (required = false, value = "Type of email this member asked to get (‘html’ or ‘text’).")
	@JsonProperty (value = "email_type")
	private String emailType;

	/** Subscriber’s current status. Possible Values: subscribed, unsubscribed, cleaned, pending, transactional. */
	@ApiModelProperty (required = true, value = "Subscriber’s current status. Possible Values: subscribed, unsubscribed, cleaned, pending, transactional.")
	@NotNull
	private String status;

	/** An individual merge var and value for a member. */
	@ApiModelProperty (required = false, value = "An individual merge var and value for a member.")
	@JsonProperty (value = "merge_fields")
	private Map<String, String> mergeFields;

	/** The key of this object’s properties is the ID of the interest in question. */
	@ApiModelProperty (required = false, value = "The key of this object’s properties is the ID of the interest in question.")
	private Map<String, Boolean> interests;

	/** If set/detected, the subscriber’s language. */
	@ApiModelProperty (required = false, value = "If set/detected, the subscriber’s language.")
	private String language;

	/** VIP status for subscriber. */
	@ApiModelProperty (required = false, value = "VIP status for subscriber.")
	private Boolean vip;

	/** Subscriber location information. */
	@ApiModelProperty (required = false, value = "Subscriber location information.")
	private Location location;

	/** IP address the subscriber signed up from. */
	@ApiModelProperty (required = false, value = "IP address the subscriber signed up from.")
	@JsonProperty (value = "ip_signup")
	private String ipSignup;

	/** The date and time the subscriber signed up for the list. */
	@ApiModelProperty (required = false, value = "The date and time the subscriber signed up for the list.")
	@JsonProperty (value = "timestamp_signup")
	private String timestampSignup;

	/** The IP address the subscriber used to confirm their opt-in status. */
	@ApiModelProperty (required = false, value = "The IP address the subscriber used to confirm their opt-in status.")
	@JsonProperty (value = "ip_opt")
	private String ipOpt;

	/** The date and time the subscribe confirmed their opt-in status. */
	@ApiModelProperty (required = false, value = "The date and time the subscribe confirmed their opt-in status.")
	@JsonProperty (value = "timestamp_opt")
	private String timestampOpt;

	/**
	 * Gets the email address.
	 *
	 * @return the email address
	 */
	public String getEmailAddress ()
	{
		return emailAddress;
	}

	/**
	 * Sets the email address.
	 *
	 * @param emailAddress the new email address
	 */
	public void setEmailAddress (String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	/**
	 * Gets the email type.
	 *
	 * @return the email type
	 */
	public String getEmailType ()
	{
		return emailType;
	}

	/**
	 * Sets the email type.
	 *
	 * @param emailType the new email type
	 */
	public void setEmailType (String emailType)
	{
		this.emailType = emailType;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus ()
	{
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus (String status)
	{
		this.status = status;
	}

	/**
	 * Gets the merge fields.
	 *
	 * @return the merge fields
	 */
	public Map<String, String> getMergeFields ()
	{
		return mergeFields;
	}

	/**
	 * Sets the merge fields.
	 *
	 * @param mergeFields the merge fields
	 */
	public void setMergeFields (Map<String, String> mergeFields)
	{
		this.mergeFields = mergeFields;
	}

	/**
	 * Gets the interests.
	 *
	 * @return the interests
	 */
	public Map<String, Boolean> getInterests ()
	{
		return interests;
	}

	/**
	 * Sets the interests.
	 *
	 * @param interests the interests
	 */
	public void setInterests (Map<String, Boolean> interests)
	{
		this.interests = interests;
	}

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage ()
	{
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage (String language)
	{
		this.language = language;
	}

	/**
	 * Gets the vip.
	 *
	 * @return the vip
	 */
	public Boolean getVip ()
	{
		return vip;
	}

	/**
	 * Sets the vip.
	 *
	 * @param vip the new vip
	 */
	public void setVip (Boolean vip)
	{
		this.vip = vip;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public Location getLocation ()
	{
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation (Location location)
	{
		this.location = location;
	}

	/**
	 * Gets the ip signup.
	 *
	 * @return the ip signup
	 */
	public String getIpSignup ()
	{
		return ipSignup;
	}

	/**
	 * Sets the ip signup.
	 *
	 * @param ipSignup the new ip signup
	 */
	public void setIpSignup (String ipSignup)
	{
		this.ipSignup = ipSignup;
	}

	/**
	 * Gets the timestamp signup.
	 *
	 * @return the timestamp signup
	 */
	public String getTimestampSignup ()
	{
		return timestampSignup;
	}

	/**
	 * Sets the timestamp signup.
	 *
	 * @param timestampSignup the new timestamp signup
	 */
	public void setTimestampSignup (String timestampSignup)
	{
		this.timestampSignup = timestampSignup;
	}

	/**
	 * Gets the ip opt.
	 *
	 * @return the ip opt
	 */
	public String getIpOpt ()
	{
		return ipOpt;
	}

	/**
	 * Sets the ip opt.
	 *
	 * @param ipOpt the new ip opt
	 */
	public void setIpOpt (String ipOpt)
	{
		this.ipOpt = ipOpt;
	}

	/**
	 * Gets the timestamp opt.
	 *
	 * @return the timestamp opt
	 */
	public String getTimestampOpt ()
	{
		return timestampOpt;
	}

	/**
	 * Sets the timestamp opt.
	 *
	 * @param timestampOpt the new timestamp opt
	 */
	public void setTimestampOpt (String timestampOpt)
	{
		this.timestampOpt = timestampOpt;
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
		MailchimpAddListMemberRequestBody castOther = (MailchimpAddListMemberRequestBody) other;
		return new EqualsBuilder().append(emailAddress, castOther.emailAddress).append(emailType, castOther.emailType).append(status, castOther.status).append(mergeFields, castOther.mergeFields).append(interests, castOther.interests).append(language, castOther.language).append(vip, castOther.vip).append(location, castOther.location).append(ipSignup, castOther.ipSignup).append(timestampSignup, castOther.timestampSignup).append(ipOpt, castOther.ipOpt).append(timestampOpt, castOther.timestampOpt).isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(-2115626711, -458679785).append(emailAddress).append(emailType).append(status).append(mergeFields).append(interests).append(language).append(vip).append(location).append(ipSignup).append(timestampSignup).append(ipOpt).append(timestampOpt).toHashCode();
	}
}
