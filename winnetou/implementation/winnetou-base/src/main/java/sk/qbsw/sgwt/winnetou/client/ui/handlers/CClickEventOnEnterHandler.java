package sk.qbsw.sgwt.winnetou.client.ui.handlers;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

/**
 * Handling click on text item in the form
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CClickEventOnEnterHandler implements KeyPressHandler
{
	private ButtonItem source;
	private DynamicForm form;

	public CClickEventOnEnterHandler(ButtonItem source, DynamicForm form)
	{
		this.source = source;
		this.form = form;
	}

	public void onKeyPress(KeyPressEvent event)
	{
		if (event.getCharacterValue() != null)
		{
			int character = event.getCharacterValue();
			if (character == 13)
			{
				source.fireEvent(new ClickEvent(form.getJsObj()));
			}
		}
	}

}
