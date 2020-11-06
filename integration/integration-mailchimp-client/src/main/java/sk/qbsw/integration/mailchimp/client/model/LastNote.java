package sk.qbsw.integration.mailchimp.client.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The mailchimp last note.
 * 
 * @author Juraj Vrabec
 * @version 2.6.0
 * @since 1.17.0
 */
public class LastNote extends BaseEntity
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5492932472006858217L;

	/** The note id. */
	@Schema (description = "The note id.")
	@JsonProperty (value = "note_id")
	private Integer noteId;

	/** The date and time the note was created. */
	@Schema (description = "The date and time the note was created.")
	@JsonProperty (value = "created_at")
	private String createdAt;

	/** The author of the note. */
	@Schema (description = "The author of the note.")
	@JsonProperty (value = "created_by")
	private String createdBy;

	/** The content of the note. */
	@Schema (description = "The content of the note.")
	private String note;

	/**
	 * Gets the note id.
	 *
	 * @return the note id
	 */
	public Integer getNoteId ()
	{
		return noteId;
	}

	/**
	 * Sets the note id.
	 *
	 * @param noteId the new note id
	 */
	public void setNoteId (Integer noteId)
	{
		this.noteId = noteId;
	}

	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public String getCreatedAt ()
	{
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the new created at
	 */
	public void setCreatedAt (String createdAt)
	{
		this.createdAt = createdAt;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy ()
	{
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy (String createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * Gets the note.
	 *
	 * @return the note
	 */
	public String getNote ()
	{
		return note;
	}

	/**
	 * Sets the note.
	 *
	 * @param note the new note
	 */
	public void setNote (String note)
	{
		this.note = note;
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
		LastNote castOther = (LastNote) other;
		return new EqualsBuilder().append(noteId, castOther.noteId).append(createdAt, castOther.createdAt).append(createdBy, castOther.createdBy).append(note, castOther.note).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(782204415, -93666073).append(noteId).append(createdAt).append(createdBy).append(note).toHashCode();
	}
}
