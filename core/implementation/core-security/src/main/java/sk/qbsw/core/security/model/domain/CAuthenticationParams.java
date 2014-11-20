/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * The authentication parameters for user.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.12.0
 * @since 1.6.0
 */
@Entity
@Table (name = "t_auth_params", schema = "sec")
public class CAuthenticationParams extends ASecurityChangeEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "t_auth_params_pkid_generator", sequenceName = "sec.t_auth_params_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_auth_params_pkid_generator")
	@Column (name = "pk_id")
	@Expose
	private Long id;

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

	/** The password type. */
	@Column (name = "c_password_type", nullable = false)
	@Enumerated (EnumType.STRING)
	private EPasswordType passwordType = EPasswordType.DURABLE;

	/**
	 * Instantiates a authentication params.
	 */
	public CAuthenticationParams ()
	{
	}

	/**
	 * Gets the pk id.
	 * 
	 * @return the pk id
	 */
	public Long getId ()
	{
		return this.id;
	}

	/**
	 * Sets the pk id.
	 * 
	 * @param id
	 *            the new pk id
	 */
	public void setId (Long id)
	{
		this.id = id;
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

	/**
	 * Gets the password type.
	 *
	 * @return the password type
	 */
	public EPasswordType getPasswordType ()
	{
		return passwordType;
	}

	/**
	 * Sets the password type.
	 *
	 * @param passwordType the new password type
	 */
	public void setPasswordType (EPasswordType passwordType)
	{
		this.passwordType = passwordType;
	}
}
