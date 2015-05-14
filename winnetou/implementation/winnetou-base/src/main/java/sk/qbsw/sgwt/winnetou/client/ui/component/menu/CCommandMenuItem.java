package sk.qbsw.sgwt.winnetou.client.ui.component.menu;

import sk.qbsw.sgwt.winnetou.client.model.menu.CMenuTabSubItem;
import sk.qbsw.sgwt.winnetou.client.ui.component.menu.commands.CMenuCommandsHolder;
import sk.qbsw.sgwt.winnetou.client.ui.component.menu.commands.IMenuCommand;

import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

/**
 * Menu item which executes command when clicked on the menu item.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CCommandMenuItem extends MenuItem
{
	/**
	 * Model related to this subItem
	 */
	private CMenuTabSubItem subItem;

	public CMenuTabSubItem getSubItem()
	{
		return subItem;
	}

	public void setSubItem(CMenuTabSubItem subItem)
	{
		this.subItem = subItem;
	}

	public CCommandMenuItem()
	{
		super();
		addClickHandler(new CMenuClickHandler());
	}

	/**
	 * Handler for executing command.
	 * 
	 * @author Dalibor Rak
	 */
	private class CMenuClickHandler implements ClickHandler
	{
		/**
		 * 
		 */
		public CMenuClickHandler()
		{
			super();
		}

		/**
		 * Shows target screen (and hides previous visible one).
		 */
		public void onClick(MenuItemClickEvent event)
		{
			String commandId = getSubItem().getCommand();
			
			IMenuCommand command = CMenuCommandsHolder.getInstance().getCommand(commandId);
			command.execute(getSubItem());
		}
	}
}
