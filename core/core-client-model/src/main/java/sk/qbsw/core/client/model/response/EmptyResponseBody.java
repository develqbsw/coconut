package sk.qbsw.core.client.model.response;

/**
 * The empty response body.
 *
 * @author Roman Farkaš
 * @version 1.18.0
 * @since 1.18.0
 */
public class EmptyResponseBody extends BaseResponseBody
{
	private static final long serialVersionUID = 8886662664456864269L;

	public static EmptyResponseBody build ()
	{
		return new EmptyResponseBody();
	}
}
