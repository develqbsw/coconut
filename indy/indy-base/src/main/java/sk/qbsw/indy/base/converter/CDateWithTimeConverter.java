package sk.qbsw.indy.base.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CDateWithTimeConverter implements IConverter<Date>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CDateWithTimeConverter.class);

	@Override
	public Date convertToObject (String value, Locale locale)
	{
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale);
		try
		{
			return formatter.parse(value);
		}
		catch (ParseException e)
		{
			LOGGER.error("Error parsing calendar_with_time text");
		}
		return null;
	}

	@Override
	public String convertToString (Date value, Locale locale)
	{
		if (value != null)
		{
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale);
			return formatter.format((Date) value);
		}
		else
		{
			return null;
		}
	}


}
