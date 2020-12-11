package sk.qbsw.integration.mailchimp.client.model.response;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import sk.qbsw.integration.mailchimp.client.model.*;

/**
 * The mailchimp add list member response body.
 *
 * @author Juraj Vrabec
 * @version 2.6.0
 * @since 1.17.0
 */
public class MailchimpAddListMemberResponseBody extends BaseEntity
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5943804213950865293L;

	/** The MD5 hash of the lowercase version of the list member’s email address. */
	@Schema (description = "The MD5 hash of the lowercase version of the list member’s email address.")
	private String id;

	/** Email address for a subscriber. */
	@Schema (description = "Email address for a subscriber.")
	@JsonProperty (value = "email_address")
	private String emailAddress;

	/** An identifier for the address across all of MailChimp. */
	@Schema (description = "An identifier for the address across all of MailChimp.")
	@JsonProperty (value = "unique_email_id")
	private String uniqueEmailId;

	/** Type of email this member asked to get (‘html’ or ‘text’). */
	@Schema (description = "Type of email this member asked to get (‘html’ or ‘text’).")
	@JsonProperty (value = "email_type")
	private String emailType;

	/** Subscriber’s current status. Possible Values: subscribed, unsubscribed, cleaned, pending, transactional. */
	@Schema (description = "Subscriber’s current status. Possible Values: subscribed, unsubscribed, cleaned, pending, transactional.")
	private String status;

	/** An individual merge var and value for a member. */
	@Schema (description = "An individual merge var and value for a member.")
	@JsonProperty (value = "merge_fields")
	private Map<String, String> mergeFields;

	/** The key of this object’s properties is the ID of the interest in question. */
	@Schema (description = "The key of this object’s properties is the ID of the interest in question.")
	private Map<String, String> interests;

	/** Open and click rates for this subscriber. */
	@Schema (description = "Open and click rates for this subscriber.")
	private Stats stats;

	/** IP address the subscriber signed up from. */
	@Schema (description = "IP address the subscriber signed up from.")
	@JsonProperty (value = "ip_signup")
	private String ipSignup;

	/** The date and time the subscriber signed up for the list. */
	@Schema (description = "The date and time the subscriber signed up for the list.")
	@JsonProperty (value = "timestamp_signup")
	private String timestampSignup;

	/** The IP address the subscriber used to confirm their opt-in status. */
	@Schema (description = "The IP address the subscriber used to confirm their opt-in status.")
	@JsonProperty (value = "ip_opt")
	private String ipOpt;

	/** The date and time the subscribe confirmed their opt-in status. */
	@Schema (description = "The date and time the subscribe confirmed their opt-in status.")
	@JsonProperty (value = "timestamp_opt")
	private String timestampOpt;

	/** Star rating for this member, between 1 and 5. */
	@Schema (description = "Star rating for this member, between 1 and 5.")
	@JsonProperty (value = "member_rating")
	private Integer memberRating;

	/** The date and time the member’s info was last changed. */
	@Schema (description = "The date and time the member’s info was last changed.")
	@JsonProperty (value = "last_changed")
	private String lastChanged;

	/** If set/detected, the subscriber’s language.e */
	@Schema (description = "If set/detected, the subscriber’s language.e")
	private String language;

	/** VIP status for subscriber. */
	@Schema (description = "VIP status for subscriber.")
	private Boolean vip;

	/** The list member’s email client. */
	@Schema (description = "The list member’s email client.")
	@JsonProperty (value = "email_client")
	private String emailClient;

	/** Subscriber location information. */
	@Schema (description = "Subscriber location information.")
	private Location location;

	/** The most recent Note added about this member. */
	@Schema (description = "The most recent Note added about this member.")
	@JsonProperty (value = "last_note")
	private LastNote lastNote;

	/** The list id. */
	@Schema (description = "The list id.")
	@JsonProperty (value = "list_id")
	private String listId;

	/** A list of link types and descriptions for the API schema documents. */
	@Schema (description = "A list of link types and descriptions for the API schema documents.")
	@JsonProperty (value = "_links")
	private List<Link> links;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId ()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId (String id)
	{
		this.id = id;
	}

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
	 * Gets the unique email id.
	 *
	 * @return the unique email id
	 */
	public String getUniqueEmailId ()
	{
		return uniqueEmailId;
	}

	/**
	 * Sets the unique email id.
	 *
	 * @param uniqueEmailId the new unique email id
	 */
	public void setUniqueEmailId (String uniqueEmailId)
	{
		this.uniqueEmailId = uniqueEmailId;
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
	public Map<String, String> getInterests ()
	{
		return interests;
	}

	/**
	 * Sets the interests.
	 *
	 * @param interests the interests
	 */
	public void setInterests (Map<String, String> interests)
	{
		this.interests = interests;
	}

	/**
	 * Gets the stats.
	 *
	 * @return the stats
	 */
	public Stats getStats ()
	{
		return stats;
	}

	/**
	 * Sets the stats.
	 *
	 * @param stats the new stats
	 */
	public void setStats (Stats stats)
	{
		this.stats = stats;
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

	/**
	 * Gets the member rating.
	 *
	 * @return the member rating
	 */
	public Integer getMemberRating ()
	{
		return memberRating;
	}

	/**
	 * Sets the member rating.
	 *
	 * @param memberRating the new member rating
	 */
	public void setMemberRating (Integer memberRating)
	{
		this.memberRating = memberRating;
	}

	/**
	 * Gets the last changed.
	 *
	 * @return the last changed
	 */
	public String getLastChanged ()
	{
		return lastChanged;
	}

	/**
	 * Sets the last changed.
	 *
	 * @param lastChanged the new last changed
	 */
	public void setLastChanged (String lastChanged)
	{
		this.lastChanged = lastChanged;
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
	 * Gets the email client.
	 *
	 * @return the email client
	 */
	public String getEmailClient ()
	{
		return emailClient;
	}

	/**
	 * Sets the email client.
	 *
	 * @param emailClient the new email client
	 */
	public void setEmailClient (String emailClient)
	{
		this.emailClient = emailClient;
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
	 * Gets the last note.
	 *
	 * @return the last note
	 */
	public LastNote getLastNote ()
	{
		return lastNote;
	}

	/**
	 * Sets the last note.
	 *
	 * @param lastNote the new last note
	 */
	public void setLastNote (LastNote lastNote)
	{
		this.lastNote = lastNote;
	}

	/**
	 * Gets the list id.
	 *
	 * @return the list id
	 */
	public String getListId ()
	{
		return listId;
	}

	/**
	 * Sets the list id.
	 *
	 * @param listId the new list id
	 */
	public void setListId (String listId)
	{
		this.listId = listId;
	}

	/**
	 * Gets the links.
	 *
	 * @return the links
	 */
	public List<Link> getLinks ()
	{
		return links;
	}

	/**
	 * Sets the links.
	 *
	 * @param links the new links
	 */
	public void setLinks (List<Link> links)
	{
		this.links = links;
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
		MailchimpAddListMemberResponseBody castOther = (MailchimpAddListMemberResponseBody) other;
		return new EqualsBuilder().append(id, castOther.id).append(emailAddress, castOther.emailAddress).append(uniqueEmailId, castOther.uniqueEmailId).append(emailType, castOther.emailType).append(status, castOther.status).append(mergeFields, castOther.mergeFields).append(interests, castOther.interests).append(stats, castOther.stats).append(ipSignup, castOther.ipSignup).append(timestampSignup, castOther.timestampSignup).append(ipOpt, castOther.ipOpt).append(timestampOpt, castOther.timestampOpt).append(memberRating, castOther.memberRating).append(lastChanged, castOther.lastChanged).append(language, castOther.language).append(vip, castOther.vip).append(emailClient, castOther.emailClient).append(location, castOther.location).append(lastNote, castOther.lastNote).append(listId, castOther.listId).append(links, castOther.links).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(882401045, -1657288429).append(id).append(emailAddress).append(uniqueEmailId).append(emailType).append(status).append(mergeFields).append(interests).append(stats).append(ipSignup).append(timestampSignup).append(ipOpt).append(timestampOpt).append(memberRating).append(lastChanged).append(language).append(vip).append(emailClient).append(location).append(lastNote).append(listId).append(links).toHashCode();
	}
}
