package sk.qbsw.core.client.model.response;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The error response body - it contains the code and the message.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class ErrorResponseBody extends BaseResponseBody
{
	@ApiModelProperty (required = true, value = "The error code")
	private String code;

	@ApiModelProperty (required = true, value = "The error message")
	private String message;

	/**
	 * Gets code variable name.
	 *
	 * @return the code variable name
	 */
	public static String getCodeVariableName ()
	{
		return "code";
	}

	/**
	 * Gets message variable name.
	 *
	 * @return the message variable name
	 */
	public static String getMessageVariableName ()
	{
		return "message";
	}

	/**
	 * Gets code.
	 *
	 * @return the code
	 */
	public String getCode ()
	{
		return code;
	}

	/**
	 * Sets code.
	 *
	 * @param code the code
	 */
	public void setCode (String code)
	{
		this.code = code;
	}

	/**
	 * Gets message.
	 *
	 * @return the message
	 */
	public String getMessage ()
	{
		return message;
	}

	/**
	 * Sets message.
	 *
	 * @param message the message
	 */
	public void setMessage (String message)
	{
		this.message = message;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		ErrorResponseBody that = (ErrorResponseBody) o;

		return new EqualsBuilder().append(code, that.code).append(message, that.message).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).append(code).append(message).toHashCode();
	}
}

