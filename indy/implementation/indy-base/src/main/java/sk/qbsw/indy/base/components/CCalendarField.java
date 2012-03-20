package sk.qbsw.indy.base.components;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import sk.qbsw.indy.base.converter.CCalendarConverter;

/**
 * Field with calendar renderer support.
 * 
 * @author rosenberg
 *
 * @param <Calendar>
 */
public class CCalendarField<Calendar> extends TextField<Calendar>
{

	private static final long serialVersionUID = 1L;

	CCalendarConverter calendarConverter = new CCalendarConverter();

	public CCalendarField (String id)
	{
		super(id);
	}

	public CCalendarField (String id, Class<Calendar> type)
	{
		super(id, type);
	}


	public CCalendarField (String id, IModel<Calendar> model)
	{
		super(id, model);
	}

	public CCalendarField (String id, IModel<Calendar> model, Class<Calendar> type)
	{
		super(id, model, type);
	}

	@Override
	public <C>IConverter<C> getConverter (Class<C> type)
	{
		return this.calendarConverter;
	}
}
