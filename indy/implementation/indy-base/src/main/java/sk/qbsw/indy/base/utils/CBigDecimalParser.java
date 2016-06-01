package sk.qbsw.indy.base.utils;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.BigDecimalConverter;

/**
 * Default money parser
 * @author rosenberg
 * @since 0.1.2
 * @version 0.1.2 
 */
public class CBigDecimalParser
{

	public static BigDecimal parseBigDecimal (String s, Locale locale)
	{
		BigDecimalConverter converter = new BigDecimalConverter();
		return (BigDecimal) converter.convertToObject(s, locale);
	}

	public static BigDecimal parseBigDecimal (Object so, Locale locale)
	{
		BigDecimalConverter converter = new BigDecimalConverter();
		return (BigDecimal) converter.convertToObject(so.toString(), locale);
	}

}
