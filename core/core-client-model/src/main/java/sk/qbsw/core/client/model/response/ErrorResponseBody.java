package sk.qbsw.core.client.model.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The error response body - it contains the code and the message.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.0
 */
public class ErrorResponseBody extends BaseResponseBody
{
	@Schema (required = true, description = "The error code")
	private String code;

	@Schema (required = true, description = "The error message")
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

