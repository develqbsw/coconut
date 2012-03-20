package sk.qbsw.indy.base.validator;


import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;

import sk.qbsw.indy.base.utils.CBigDecimalParser;

/**
 * Money validate utility - not used now!!!.
 * @author rosenberg
 * @since 0.1.2
 * @version 0.1.2 
 */
public class CBigDecimalMoneyValidator extends AbstractValidator<BigDecimal>
{

	private static final long serialVersionUID = 5638024896256902410L;

	private Locale locale = new Locale("sk");

	@Override
	protected void onValidate (IValidatable<BigDecimal> validatable)
	{
		try
		{
			// First check that a normal conversion works
			@SuppressWarnings ("unused")
			BigDecimal bigDecimal = CBigDecimalParser.parseBigDecimal(validatable.getValue(), getSessionLocale());

			// Now check if the only non digit character is the decimal sign
			// corresponding to the locale and that it appears only once.
			checkDecimalSeparator(validatable.getValue(), getSessionLocale());
		}
		catch (Exception e)
		{
			error(validatable);
		}
	}

	private void checkDecimalSeparator (Object value, Locale sessionLocale)
	{
		char decimalSeperator = new DecimalFormatSymbols(sessionLocale).getDecimalSeparator();

		Pattern p = Pattern.compile("^\\d+(\\" + decimalSeperator + "\\d{2})?$");
		Matcher m = p.matcher(value.toString());
		if (!m.find())
		{
			throw new NumberFormatException("Invalid price.");
		}
	}

	public void setLocale (Locale locale)
	{
		this.locale = locale;
	}


	private Locale getSessionLocale ()
	{
		return this.locale;
	}

}
