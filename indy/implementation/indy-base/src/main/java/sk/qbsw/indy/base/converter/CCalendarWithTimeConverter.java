package sk.qbsw.indy.base.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.AbsoluteTimeDateFormat;
import org.apache.wicket.Session;
import org.apache.wicket.util.convert.IConverter;

/**
 * Converter for calendar to string - with time also
 * 
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
public class CCalendarWithTimeConverter implements IConverter<Calendar>
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
			DateFormat formatter = AbsoluteTimeDateFormat.getDateTimeInstance(AbsoluteTimeDateFormat.SHORT, AbsoluteTimeDateFormat.SHORT, locale);
			Date date = formatter.parse(value);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		}
		catch (ParseException ex)
		{
			Logger.getLogger(CCalendarWithTimeConverter.class).error("Error parsing calendar_with_time text");
		}
		return null;
	}

	/**
	 * Converts calendar to String
	 */
	@Override
	public String convertToString (Calendar value, Locale locale)
	{
		DateFormat formatter = AbsoluteTimeDateFormat.getDateTimeInstance(AbsoluteTimeDateFormat.SHORT, AbsoluteTimeDateFormat.SHORT, Session.get().getLocale());
		return formatter.format( ((Calendar) value).getTime());
	}
}
