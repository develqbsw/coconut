package sk.qbsw.indy.base.utils;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;

/**
 * Default money model used for formatting input fields with some price.
 * @author rosenberg
 * @since 0.1.2
 * @version 0.1.2 
 */
public class CDefaultMoneyModel implements IModel<String>
{
	private static final long serialVersionUID = 1479000955482442517L;

	private final IModel<BigDecimal> internalModel;

	private Locale locale = Session.get().getLocale();

	public CDefaultMoneyModel (IModel<BigDecimal> nestedModel)
	{
		internalModel = nestedModel;
	}

	public String getObject ()
	{
		BigDecimal value = (BigDecimal) internalModel.getObject();

		// convert BigDecimal to String
		return CBigDecimalToMoneyFormat.format(value, getSessionLocale());
	}

	public void setObject (String object)
	{
		// convert String to BigDecimal
		BigDecimal value = CBigDecimalParser.parseBigDecimal(object, getSessionLocale());
		internalModel.setObject(value);
	}

	public void detach ()
	{
		internalModel.detach();
	}

	private Locale getSessionLocale ()
	{
		// code to get the current Session Locale, omitted for brevity
		return this.locale;
	}

	public void setLocale (Locale locale)
	{
		this.locale = locale;
	}
}
