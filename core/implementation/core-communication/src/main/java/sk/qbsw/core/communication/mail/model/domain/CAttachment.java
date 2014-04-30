package sk.qbsw.core.communication.mail.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The mail attachment.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
@Entity
@Table (name = "t_attachment", schema = "apsys")
public class CAttachment implements Serializable, IEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The entity id. */
	@Id
	@SequenceGenerator (name = "t_attachment_pkid_generator", sequenceName = "apsys.t_attachment_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_attachment_pkid_generator")
	@Column (name = "pk_id", nullable = true)
	private Long id;

	/** The file name. */
	@Column (name = "c_file_name", nullable = false)
	private String fileName;

	/** The data. */
	@Lob
	@Column (name = "c_data", nullable = false)
	private byte[] data;

	/** The content type. */
	@Column (name = "c_content_type")
	private String contentType;

	/** The mail. */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_mail", nullable = false)
	private CMail mail;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.model.domain.IEntity#getId()
	 */
	public Long getId ()
	{
		return id;
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
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName ()
	{
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName (String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public byte[] getData ()
	{
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData (byte[] data)
	{
		this.data = data;
	}

	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	public String getContentType ()
	{
		return contentType;
	}

	/**
	 * Sets the content type.
	 *
	 * @param contentType the new content type
	 */
	public void setContentType (String contentType)
	{
		this.contentType = contentType;
	}

	/**
	 * Gets the mail.
	 *
	 * @return the mail
	 */
	public CMail getMail ()
	{
		return mail;
	}

	/**
	 * Sets the mail.
	 *
	 * @param mail the new mail
	 */
	public void setMail (CMail mail)
	{
		this.mail = mail;
	}
}
