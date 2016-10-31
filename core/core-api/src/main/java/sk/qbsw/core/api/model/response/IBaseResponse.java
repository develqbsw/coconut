package sk.qbsw.core.api.model.response;

/**
 * The base response.
 * 
 * @param <T> the response data type
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since   1.16.0
 */
public interface IBaseResponse<T>
{
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	T getData ();

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	void setData (T data);

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	CResponseError getError ();

	/**
	 * Sets the error.
	 *
	 * @param error the new error
	 */
	void setError (CResponseError error);

	/**
	 * Validate.
	 *
	 * @return true, if successful
	 */
	boolean validate ();
}
