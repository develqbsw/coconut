package sk.qbsw.core.logging.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sk.qbsw.core.persistence.model.domain.AEntity;

/**
 * The patch installation log.
 * 
 * @author Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
@Entity
@Table(name = "t_installation_log", schema = "log")
public class CInstallationLog extends AEntity<Long> implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6355326578030715757L;

	/** The id. */
	@Id
	@SequenceGenerator(name = "t_installation_log_pkid_generator", sequenceName = "log.t_installation_log_pk_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_installation_log_pkid_generator")
	@Column(name = "pk_id")
	private Long id;

	/** The version. */
	@Column(name = "c_version", nullable = false, unique = true)
	private String version;

	/** The name. */
	@Column(name = "c_name")
	private String name;

	/** The description. */
	@Column(name = "c_description")
	private String description;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Override
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(String version)
	{
		this.version = version;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
