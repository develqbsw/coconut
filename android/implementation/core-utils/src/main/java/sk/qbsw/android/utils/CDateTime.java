package sk.qbsw.android.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class to work with photos
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.4.0
 */
public class CDateTime
{
	/**
	 * compare days and return count of day between them
	 * @param firstDay first day to compare
	 * @param secondDay second day to compare
	 * @return 0 if is fristDay and secondDay in same day
	 * positive number if first day is bigger than secondDay
	 * negative number if first day is lower than secondDay
	 */
	public static int compareDays (Date fristDay, Date secondDay)
	{
		//return rounded time on days
		return (int) ( (fristDay.getTime() - secondDay.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * check if days of date is same
	 * @param firstDay first day to compare
	 * @param secondDay second day to compare
	 * @return true if day of days is same
	 */
	public static Boolean equalsDay (Date fristDay, Date secondDay)
	{
		Boolean isSame = Boolean.FALSE;

		Calendar fristCalendar = Calendar.getInstance();
		fristCalendar.setTime(fristDay);

		Calendar secondCalendar = Calendar.getInstance();
		secondCalendar.setTime(secondDay);

		//these is low int
		if (fristCalendar.get(Calendar.YEAR) == secondCalendar.get(Calendar.YEAR) && fristCalendar.get(Calendar.DAY_OF_YEAR) == secondCalendar.get(Calendar.DAY_OF_YEAR))
		{
			isSame = Boolean.TRUE;
		}

		return isSame;
	}

	/**
	 * method convert time in string format to time in date class this method expects timeString in standard HH:mm format pattern see java.text.SimpleDateFormat javadoc
	 * @param timeInTextFormat date in string to format
	 * @return new date with today day, month year,
	 * minutes and hours is setted from timeInTextFormat and seconds and milliseconds are zero.
	 * @throws ParseException when pattern does not match with dateString
	 */
	public static Date toTime (String timeInTextFormat) throws ParseException
	{
		return toDate(timeInTextFormat, "HH:mm");
	}

	/**
	 * convert date in string format to java.util.Date with pattern which is specified in "formatPattern" parameter
	 * @param dateString dateString to format
	 * @param formatPattern pattern of dateString whereby is dateString formatted to date for pattern see java.text.SimpleDateFormat javadoc
	 * @return formatted date
	 * @throws ParseException when pattern does not match with dateString
	 */
	public static Date toDate (String dateString, String formatPattern) throws ParseException
	{
		DateFormat formatter = new SimpleDateFormat(formatPattern, Locale.getDefault());
		return formatter.parse(dateString);
	}
	
	/**
	 * convert date to string pattern which is specified in "formatPattern" parameter
	 * @param dateString dateString to format
	 * @param formatPattern pattern of dateString whereby is date formatted to dateString for pattern see java.text.SimpleDateFormat javadoc
	 * @return string representing formatted date
	 * @throws ParseException when pattern does not match with dateString
	 */
	public static String formatDate (Date dateToFormat, String formatPattern)
	{
		DateFormat formatter = new SimpleDateFormat(formatPattern, Locale.getDefault());
		return formatter.format(dateToFormat);
	}
	
	/**
	 * convert date to string with pattern DD.MM.YYYY HH:mm
	 * @param dateString dateString to format
	 * @return string representing formatted date
	 * @throws ParseException when pattern does not match with dateString
	 */
	public static String formatDateTime (Date dateToFormat)
	{
		return formatDate(dateToFormat, "dd.MM.yyyy HH:mm");
	}
}
