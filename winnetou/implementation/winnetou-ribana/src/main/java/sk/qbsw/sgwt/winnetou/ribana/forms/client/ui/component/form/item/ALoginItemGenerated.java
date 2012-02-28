package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import com.smartgwt.client.widgets.form.fields.events.FocusEvent;
import com.smartgwt.client.widgets.form.fields.events.FocusHandler;
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;

/**
 * Class represents Login item with length check (min 8 characters and max 20
 * chars)
 * 
 * @author Dalibor Rak
 * @Version 0.1
 * @since 0.1
 */
public abstract class ALoginItemGenerated extends CLoginItem
{
	public ALoginItemGenerated()
	{
		super();

		LengthRangeValidator validatorLength = new LengthRangeValidator();
		validatorLength.setMin(6);
		validatorLength.setMax(20);
		setValidators(validatorLength);

		addFocusHandler(new FocusHandler()
		{
			public void onFocus(FocusEvent event)
			{
				String val = (String) getValue();

				if (val == null || val.length() == 0)
				{
					generate();
				}
			}
		});
	}

	/**
	 * Generates login in format
	 * 
	 * 		if (name != null && surname != null)
		{
			IUserService.Locator.getInstance().generateLogin(name.getValue() + "." + surname.getValue(), new AsyncCallback<String>()
			{

				public void onFailure(Throwable caught)
				{
					CClientExceptionPublisher.publish(caught);
				}

				public void onSuccess(String result)
				{
					setValue(result);
				}
			});

		}

	 */
	public abstract void generate();
}
