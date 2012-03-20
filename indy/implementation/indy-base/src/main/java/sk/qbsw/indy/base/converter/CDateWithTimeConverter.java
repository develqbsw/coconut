package sk.qbsw.indy.base.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.wicket.util.convert.IConverter;

public class CDateWithTimeConverter implements IConverter
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object convertToObject (String value, Locale locale)
	{
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale);
		try
		{
			return formatter.parse(value);
		}
		catch (ParseException e)
		{
			Logger.getLogger(CCalendarWithTimeConverter.class).error("Error parsing calendar_with_time text");
		}
		return null;
	}

	@Override
	public String convertToString (Object value, Locale locale)
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
