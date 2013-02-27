package sk.qbsw.sgwt.winnetou.client.ui.component.buttons;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.menu.Menu;

/**
 * Handler for showing menu on click on menu image
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CMenuImgButtonClickHandler implements ClickHandler
{
	/**
	 * Menu to load
	 */
	private Menu menu;

	public CMenuImgButtonClickHandler(Menu menu)
	{
		this.menu = menu;
	}

	/**
	 * Handles click
	 */
	public void onClick(ClickEvent event)
	{
		Canvas button = (Canvas) event.getSource();
		button.getPosition();
		menu.setLeft(button.getLeft() + 10);
		menu.setTop(button.getBottom() + 20);
		menu.show();
	}
}
