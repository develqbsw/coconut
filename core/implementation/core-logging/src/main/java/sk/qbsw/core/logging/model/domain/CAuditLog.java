package sk.qbsw.core.logging.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The audit log which store logs to database
 * 
 * @author Michal Lacko
 * @version 1.8.0
 * @since 1.8.0
 */
@Entity
@Table (name = "t_audit_log", schema = "log")
public class CAuditLog implements Serializable, IEntity<Long>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6355326578030715757L;

	/** The id. */
	@Id
	@SequenceGenerator (name = "t_audit_log_pkid_generator", sequenceName = "sec.t_audit_log_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_audit_log_pkid_generator")
	@Column (name = "pk_id")
	private Long id;

	/** The operation code. */
	@Column (name = "c_operation_code", nullable = false)
	private String operationCode;

	/** The user identifier. */
	@Column (name = "c_user_identifier", nullable = false)
	private String userIdentifier;

	/** The request date time. */
	@Column (name = "c_request_date_time", nullable = false)
	@Type (type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime requestDateTime;

	/** The request data. */
	@Lob
	@Type(type = "org.hibernate.type.TextType") 
	@Column (name = "c_request_data", nullable = false)
	private String requestData;

	/** The result description. */
	@Column (name = "c_result_description", nullable = true)
	private String resultDescription;

	/** The operation result. */
	@Enumerated (EnumType.STRING)
	@Column (name = "c_operation_result", nullable = true)
	private EOperationResult operationResult;

	@PrePersist
	protected void onCreate ()
	{
		this.requestDateTime = new DateTime();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId ()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId (Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the operation code.
	 *
	 * @return the operationCode
	 */
	public String getOperationCode ()
	{
		return operationCode;
	}

	/**
	 * Sets the operation code.
	 *
	 * @param operationCode the operationCode to set
	 */
	public void setOperationCode (String operationCode)
	{
		this.operationCode = operationCode;
	}

	/**
	 * Gets the user identifier.
	 *
	 * @return the userIdentifier
	 */
	public String getUserIdentifier ()
	{
		return userIdentifier;
	}

	/**
	 * Sets the user identifier.
	 *
	 * @param userIdentifier the userIdentifier to set
	 */
	public void setUserIdentifier (String userIdentifier)
	{
		this.userIdentifier = userIdentifier;
	}

	/**
	 * Gets the request date time.
	 *
	 * @return the requestDateTime
	 */
	public DateTime getRequestDateTime ()
	{
		return requestDateTime;
	}

	/**
	 * Gets the request data.
	 *
	 * @return the requestData
	 */
	public String getRequestData ()
	{
		return requestData;
	}

	/**
	 * Sets the request data.
	 *
	 * @param requestData the requestData to set
	 */
	public void setRequestData (String requestData)
	{
		this.requestData = requestData;
	}

	/**
	 * Gets the result description.
	 *
	 * @return the resultDescription
	 */
	public String getResultDescription ()
	{
		return resultDescription;
	}

	/**
	 * Sets the result description.
	 *
	 * @param resultDescription the resultDescription to set
	 */
	public void setResultDescription (String resultDescription)
	{
		this.resultDescription = resultDescription;
	}

	/**
	 * Gets the operation result.
	 *
	 * @return the operationResult
	 */
	public EOperationResult getOperationResult ()
	{
		return operationResult;
	}

	/**
	 * Sets the operation result.
	 *
	 * @param operationResult the operationResult to set
	 */
	public void setOperationResult (EOperationResult operationResult)
	{
		this.operationResult = operationResult;
	}


}
