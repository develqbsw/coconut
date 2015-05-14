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

import sk.qbsw.core.persistence.model.domain.AEntity;

/**
 * The audit log which store logs to database.
 *
 * @author Michal Lacko
 * @version 1.8.0
 * @since 1.8.0
 */
@Entity
@Table(name = "t_audit_log", schema = "log")
public class CAuditLog extends AEntity<Long> implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6355326578030715757L;

	/** The id. */
	@Id
	@SequenceGenerator (name = "t_audit_log_pkid_generator", sequenceName = "log.t_audit_log_pk_id_seq",allocationSize =500)
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_audit_log_pkid_generator")
	@Column (name = "pk_id")
	private Long id;

	/** The operation code. */
	@Column(name = "c_operation_code", nullable = false)
	private String operationCode;

	/** The user identifier. */
	//nullable true if user isn't logged
	@Column(name = "c_user_identifier", nullable = true)
	private String userIdentifier;

	/** The request date time. */
	@Column(name = "c_request_date_time", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime requestDateTime;

	/** The request data. */
	@Lob
	@Column(name = "c_request_data", nullable = false)
	private String requestData;

	/** The result description. */
	@Column(name = "c_result_description", nullable = true)
	private String resultDescription;

	/** The uuid for mapping users. */
	@Column(name = "c_uuid", length = 1024, nullable = true)
	private String uuid;

	/** The operation result. */
	@Enumerated(EnumType.STRING)
	@Column(name = "c_operation_result", nullable = true)
	private EOperationResult operationResult;

	/**
	 * On create.
	 */
	@PrePersist
	protected void onCreate()
	{
		this.requestDateTime = new DateTime();
	}

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Sets the uuid.
	 *
	 * @param uuid the new uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
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
	 * Gets the operation code.
	 *
	 * @return the operationCode
	 */
	public String getOperationCode()
	{
		return operationCode;
	}

	/**
	 * Sets the operation code.
	 *
	 * @param operationCode the operationCode to set
	 */
	public void setOperationCode(String operationCode)
	{
		this.operationCode = operationCode;
	}

	/**
	 * Gets the user identifier.
	 *
	 * @return the userIdentifier
	 */
	public String getUserIdentifier()
	{
		return userIdentifier;
	}

	/**
	 * Sets the user identifier.
	 *
	 * @param userIdentifier the userIdentifier to set
	 */
	public void setUserIdentifier(String userIdentifier)
	{
		this.userIdentifier = userIdentifier;
	}

	/**
	 * Gets the request date time.
	 *
	 * @return the requestDateTime
	 */
	public DateTime getRequestDateTime()
	{
		return requestDateTime;
	}

	/**
	 * Gets the result description.
	 *
	 * @return the resultDescription
	 */
	public String getResultDescription()
	{
		return resultDescription;
	}

	/**
	 * Sets the result description.
	 *
	 * @param resultDescription the resultDescription to set
	 */
	public void setResultDescription(String resultDescription)
	{
		this.resultDescription = resultDescription;
	}

	/**
	 * Gets the operation result.
	 *
	 * @return the operationResult
	 */
	public EOperationResult getOperationResult()
	{
		return operationResult;
	}

	/**
	 * Sets the operation result.
	 *
	 * @param operationResult the operationResult to set
	 */
	public void setOperationResult(EOperationResult operationResult)
	{
		this.operationResult = operationResult;
	}

	/**
	 * Gets the request data.
	 *
	 * @return the requestData
	 */
	public String getRequestData()
	{
		return requestData;
	}

	/**
	 * Sets the request data.
	 *
	 * @param requestData the requestData to set
	 */
	public void setRequestData(String requestData)
	{
		this.requestData = requestData;
	}

}
