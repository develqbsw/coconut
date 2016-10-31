package sk.qbsw.indy.base.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Default money values formatter
 * @author rosenberg
 * @since 0.1.2
 * @version 0.1.2 
 */
public class CBigDecimalToMoneyFormat
{

	public static String format (BigDecimal decimal, Locale locale)
	{
		if (decimal == null)
		{
			return "";
		}
		else
		{
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMinimumFractionDigits(2);
			decimalFormat.setMaximumFractionDigits(2);
			decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(locale));
			return decimalFormat.format(decimal);
		}
	}
}
