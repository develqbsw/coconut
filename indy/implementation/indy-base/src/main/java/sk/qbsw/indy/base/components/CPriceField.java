package sk.qbsw.indy.base.components;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.ValidationError;

import sk.qbsw.indy.base.converter.CPriceConverter;

/**
 * Field for price formatting filed.
 * 
 * @author rosenberg
 *
 * @param <BigDecimal>
 */
public class CPriceField<BigDecimal> extends TextField<BigDecimal>
{

	private static final long serialVersionUID = 1L;

	CPriceConverter priceConverter = new CPriceConverter();

	private String fieldName;

	public CPriceField (String id)
	{
		super(id);
		this.fieldName = id;
	}

	public CPriceField (String id, IModel<BigDecimal> model)
	{
		super(id, model);
		this.fieldName = id;
	}


	public CPriceField (String id, IModel<BigDecimal> model, Class<BigDecimal> type)
	{
		super(id, model, type);
		this.fieldName = id;
	}

	@SuppressWarnings ("unchecked")
	@Override
	public <C>IConverter<C> getConverter (Class<C> type)
	{
		return (IConverter<C>) this.priceConverter;
	}

	@Override
	protected void convertInput ()
	{
		try
		{
			setConvertedInput((BigDecimal) getConverter(getType()).convertToObject(getInput(), getLocale()));
		}
		catch (ConversionException e)
		{
			reportConversionError();
		}
	}


	private void reportConversionError ()
	{
		error(new ValidationError().addMessageKey("error.value_conversion_error_in_field").setVariable("fieldName", fieldName));
	}



	public void setFieldName (String fieldName)
	{
		this.fieldName = fieldName;
	}

}
