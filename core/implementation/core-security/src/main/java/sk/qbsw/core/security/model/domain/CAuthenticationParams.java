/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sk.qbsw.core.persistence.model.domain.IEntity;

import com.google.gson.annotations.Expose;

/**
 * The authentication parameters for user.
 * 
 * @author Tomas Lauro
 * @version 1.7.2
 * @since 1.6.0
 */
@Entity
@Table (name = "t_auth_params", schema = "sec")
public class CAuthenticationParams implements Serializable, IEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "t_auth_params_pkid_generator", sequenceName = "sec.t_auth_params_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_auth_params_pkid_generator")
	@Column (name = "pk_id")
	@Expose
	private Long pkId;

	/** The password. */
	private String password;

	/** Password digest. */
	private String passwordDigest;

	/** The PIN code. */
	private String pin;

	/** The user. */
	@OneToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_user", nullable = false)
	private CUser user;

	/**
	 * Instantiates a authentication params.
	 */
	public CAuthenticationParams ()
	{
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.model.domain.IEntity#getId()
	 */
	@Override
	public Long getId ()
	{
		return getPkId();
	}

	/**
	 * Gets the pk id.
	 * 
	 * @return the pk id
	 */
	public Long getPkId ()
	{
		return this.pkId;
	}

	/**
	 * Sets the pk id.
	 * 
	 * @param pkId
	 *            the new pk id
	 */
	public void setPkId (Long pkId)
	{
		this.pkId = pkId;
	}

	/**
	 * Gets the password digest.
	 * 
	 * @return the password digest
	 */
	public String getPasswordDigest ()
	{
		return passwordDigest;
	}

	/**
	 * Sets the password digest.
	 * 
	 * @param passwordDigest
	 *            the new password digest
	 */
	public void setPasswordDigest (String passwordDigest)
	{
		this.passwordDigest = passwordDigest;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword ()
	{
		return this.password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword (String password)
	{
		this.password = password;
	}

	/**
	 * Gets the pin.
	 * 
	 * @return the pin
	 */
	public String getPin ()
	{
		return pin;
	}

	/**
	 * Sets the pin.
	 * 
	 * @param pin
	 *            the new pin
	 */
	public void setPin (String pin)
	{
		this.pin = pin;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public CUser getUser ()
	{
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser (CUser user)
	{
		this.user = user;
	}
}
