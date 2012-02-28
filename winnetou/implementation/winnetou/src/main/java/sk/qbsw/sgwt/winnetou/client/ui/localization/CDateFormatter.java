package sk.qbsw.sgwt.winnetou.client.ui.localization;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.util.DateDisplayFormatter;
import com.smartgwt.client.util.DateInputFormatter;
import com.smartgwt.client.util.DateUtil;

/**
 * Utility class for setting DateFormat in application
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CDateFormatter
{
	/**
	 * Sets default date format fo normal, short and input date
	 */
	public static void setDefaultFormat()
	{
		DateUtil.setNormalDateDisplayFormatter(new DateDisplayFormatter()
		{
			public String format(Date date)
			{
				try
				{
					if (date == null)
						return null;
					// you'll probably want to create the DateTimeFormat outside
					// this method.
					// here for illustration purposes
					DateTimeFormat dateFormatter = DateTimeFormat.getFormat("d.M.yyyy");
					String format = dateFormatter.format(date);
					return format;
				} catch (IllegalArgumentException ex)
				{
					return date.toString();
				}
			}
		});

		DateUtil.setShortDateDisplayFormatter(new DateDisplayFormatter()

		{
			public String format(Date date)
			{
				try
				{
					if (date == null)
						return null;
					// you'll probably want to create the DateTimeFormat outside
					// this method.
					// here for illustration purposes
					DateTimeFormat dateFormatter = DateTimeFormat.getFormat("d.M.yyyy");
					String format = dateFormatter.format(date);
					return format;
				} catch (IllegalArgumentException ex)
				{
					return date.toString();
				}

			}
		});

		DateUtil.setDateInputFormatter(new DateInputFormatter()
		{
			public Date parse(String dateString)
			{
				final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd.MM.yyyy");
				Date date = dateFormatter.parse(dateString);
				return date;
			}
		});
	}
}
