package sk.qbsw.indy.base.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converter for calendar to string
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.11.10
 * @since 1.0.0
 */
public class CTimeConverter implements IConverter<Calendar>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CTimeConverter.class);

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
			LOGGER.error("Error parsing calendar text");
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
