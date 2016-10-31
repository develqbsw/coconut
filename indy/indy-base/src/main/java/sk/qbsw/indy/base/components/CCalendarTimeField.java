package sk.qbsw.indy.base.components;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import sk.qbsw.indy.base.converter.CCalendarWithTimeConverter;

/**
 * Field with calendar+time renderer support.
 * 
 * @author rosenberg
 *
 * @param <Calendar>
 */
public class CCalendarTimeField<Calendar> extends TextField<Calendar>
{

	private static final long serialVersionUID = 1L;

	CCalendarWithTimeConverter calendarConverter = new CCalendarWithTimeConverter();

	public CCalendarTimeField (String id)
	{
		super(id);
	}

	public CCalendarTimeField (String id, Class<Calendar> type)
	{
		super(id, type);
	}


	public CCalendarTimeField (String id, IModel<Calendar> model)
	{
		super(id, model);
	}

	public CCalendarTimeField (String id, IModel<Calendar> model, Class<Calendar> type)
	{
		super(id, model, type);
	}

	@SuppressWarnings ("unchecked")
	@Override
	public <C>IConverter<C> getConverter (Class<C> type)
	{
		return (IConverter<C>) this.calendarConverter;
	}

}
