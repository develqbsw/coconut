package sk.qbsw.indy.base.renderer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

/**
 * Date choice renderer. Render date in localized format - month year 
 * 
 * @author Tomas Leken
 * @version 1.0
 * @since 1.0
 *
 */
public class CDateChoiceRenderer extends ChoiceRenderer<Calendar>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Session session;
	private Boolean year;

	/**
	 * Constructor.
	 * 
	 * @param session active session
	 */
	public CDateChoiceRenderer (Session session, Boolean year)
	{
		this.session = session;
		this.year = year;
	}

	/**
	 * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getDisplayValue(Object)
	 */
	@Override
	public Object getDisplayValue (Calendar calendar)
	{
		Date d = calendar.getTime();
		String dateYear = (year) ? " " + calendar.get(Calendar.YEAR) : "";
		String dateString = new SimpleDateFormat("MMMM", session.getLocale()).format(d) + dateYear;

		return dateString;
	}
}
