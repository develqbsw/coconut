package sk.qbsw.core.base.threadlocal;

/**
 * The Class CThreadLocalStorage.
 * 
 * @version 1.13.0
 * @since 1.13.0
 * @author Dalibor Rak
 */
public class CThreadLocalStorage extends AThreadLocalStorage {

	/**
	 * Gets the unique request id value.
	 *
	 * @return the unique request id value
	 */
	public static String getUniqueRequestIdValue() {
		return AThreadLocalStorage.get(IThreadLocalStorage.KEY_UNIQUE_REQUEST_ID);
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user
	 */
	public static String getUserName() {
		return AThreadLocalStorage.get(IThreadLocalStorage.KEY_USER);
	}
}
