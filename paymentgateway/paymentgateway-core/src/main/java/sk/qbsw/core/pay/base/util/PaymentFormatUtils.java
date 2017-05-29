/**
 * 
 */
package sk.qbsw.core.pay.base.util;

import org.apache.commons.lang3.StringUtils;

/**
 * pomocne metody na konvertovanie formatov 
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class PaymentFormatUtils
{
	private static final char PAD_CHAR = '0';
	private static final int MANDATORY_DIGITS_NUM = 10;
	private static final int MANDATORY_DIGITS_NUM_KS = 4;

	/**
	 * @param vs
	 * @return
	 * @author martinkovic
	 * @version 1.15.0
	 * @since 1.15.0 
	 */
	public static String formatVS (Long vs)
	{
		return StringUtils.leftPad(vs.toString(), MANDATORY_DIGITS_NUM, PAD_CHAR);
	}

	/**
	 * @param ss
	 * @return
	 * @author martinkovic
	 * @version 1.15.0
	 * @since 1.15.0 
	 */
	public static String formatSS (Long ss)
	{
		return StringUtils.leftPad(ss.toString(), MANDATORY_DIGITS_NUM, PAD_CHAR);
	}

	/**
	 * @param KS
	 * @return
	 * @author martinkovic
	 * @version 1.15.0
	 * @since 1.15.0 
	 */
	public static String formatKS (Long ks)
	{
		return StringUtils.leftPad(ks.toString(), MANDATORY_DIGITS_NUM_KS, PAD_CHAR);
	}

}
