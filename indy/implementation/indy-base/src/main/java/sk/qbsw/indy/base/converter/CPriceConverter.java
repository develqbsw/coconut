package sk.qbsw.indy.base.converter;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.qbsw.indy.base.utils.CBigDecimalParser;
import sk.qbsw.indy.base.utils.CBigDecimalToMoneyFormat;

/**
 * Converter for prices objects to string
 * 
 * @author rosenberg
 * @author Tomas Lauro
 * 
 * @version 1.11.10
 * @since 1.0.0
 */
public class CPriceConverter implements IConverter<BigDecimal>
{
	private static final long serialVersionUID = 1L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CPriceConverter.class);

	public CPriceConverter ()
	{
		super();
	}

	public CPriceConverter (String fieldName)
	{
		super();
	}


	/**
	 * Converts String to Calendar
	 */
	@Override
	public BigDecimal convertToObject (String value, Locale locale)
	{

		try
		{
			return CBigDecimalParser.parseBigDecimal(value, locale);
		}
		catch (Throwable ex)
		{
			LOGGER.error("Error parsing big decimal value");
			throw new ConversionException("chyba");
		}
	}

	/**
	 * Converts calendar to String
	 */
	@Override
	public String convertToString (BigDecimal value, Locale locale)
	{
		if (value != null)
		{
			return CBigDecimalToMoneyFormat.format((BigDecimal) value, locale);
		}
		return null;
	}
}
