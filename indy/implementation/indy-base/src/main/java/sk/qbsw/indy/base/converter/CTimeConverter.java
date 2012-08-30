package sk.qbsw.indy.base.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.wicket.util.convert.IConverter;

/**
 * Converter for calendar to string
 * 
 * @author Dalibor Rak
 * @version 1.0.0
 * @since 1.0.0
 */
public class CTimeConverter implements IConverter<Calendar>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Converts String to Calendar
	 */
	@Override
	public Calendar convertToObject (String value, Locale locale)
	{
		try
		{
			DateFormat formatter = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
			Date date = formatter.parse(value);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		}
		catch (ParseException ex)
		{
			Logger.getLogger(CTimeConverter.class).error("Error parsing calendar text");
		}
		return null;
	}

	/**
	 * Converts Calendar to String
	 */
	@Override
	public String convertToString (Calendar value, Locale locale)
	{
		DateFormat formatter = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
		return formatter.format( ((Calendar) value).getTime());
	}

}
