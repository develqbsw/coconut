package sk.qbsw.sgwt.winnetou.client.ui.handlers;

import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

/**
 * Handling ENTER on text item in the form
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public abstract class CFireEventOnEnterHandler implements KeyPressHandler
{
	public CFireEventOnEnterHandler()
	{
	}

	public void onKeyPress(KeyPressEvent event)
	{
		if (event.getCharacterValue() != null)
		{
			int character = event.getCharacterValue();
			if (character == 13)
			{
				fireEvent();
			}
		}
	}

	protected abstract void fireEvent();
}
