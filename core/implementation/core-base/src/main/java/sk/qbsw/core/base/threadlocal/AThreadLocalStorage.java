package sk.qbsw.core.base.threadlocal;

import org.slf4j.MDC;

/**
 * The Class AThreadLocalStorage.
 *
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.13.0
 */
public class AThreadLocalStorage {

	/**
	 * Put.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public static void put(String key, String value) {
		MDC.put(key, value);
	}

	/**
	 * Get.
	 *
	 * @param key the key
	 * @return the string
	 */
	public static String get(String key) {
		return MDC.get(key);
	}

	/**
	 * Clear.
	 */
	public static void clear() {
		MDC.clear();
	}

}
