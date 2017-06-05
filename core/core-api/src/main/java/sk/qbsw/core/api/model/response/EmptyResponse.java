package sk.qbsw.core.api.model.response;

/**
 * The empty response.
 *
 * @author Roman Farkaš
 * 
 * @version 1.18.0
 * @since 1.18.0
 */
public class EmptyResponse extends ABaseResponse
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8886662664456864269L;

	/**
	 * Creates the.
	 *
	 * @return the empty response
	 */
	public static EmptyResponse create ()
	{
		return new EmptyResponse();
	}
}
