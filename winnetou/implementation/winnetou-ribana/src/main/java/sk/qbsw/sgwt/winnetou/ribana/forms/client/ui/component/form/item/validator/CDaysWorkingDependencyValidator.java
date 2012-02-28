package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item.validator;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item.CDaysCalendarItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.IMessages;

import com.smartgwt.client.widgets.form.validator.CustomValidator;

/**
 * Checks if the user entered more work days as calendar days.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CDaysWorkingDependencyValidator extends CustomValidator
{
	private CDaysCalendarItem calendarDays;

	public CDaysWorkingDependencyValidator()
	{
		setErrorMessage(IMessages.Factory.getInstance().request_too_much_work_days());
	}

	@Override
	protected boolean condition(Object value)
	{
		if (calendarDays != null)
		{
			int calDays = (Integer) calendarDays.getValue();
			if (value != null)
			{
				int workDays = (Integer) value;
				if (calDays < workDays)
					return false;
			}
		}
		return true;
	}

	/**
	 * Sets dependent item
	 * 
	 * @param dependency
	 */
	public void setDaysCalendar(CDaysCalendarItem dependency)
	{
		this.calendarDays = dependency;
	}

}
