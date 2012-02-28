package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.window;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

/**
 * Menu generator for Active Icon in header of window
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 */
public class CWindowMenuGenerator
{
	/**
	 * Adds default menu actions to the Active icon
	 */
	public static Menu generateActiveIconMenu (final Window window)
	{
		Menu menu = new Menu();
		menu.setShowShadow(true);
		menu.setShadowDepth(10);

		MenuItem closeItem = new MenuItem(ILabels.Factory.getInstance().window_menu_close());
		closeItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler()
		{

			public void onClick (MenuItemClickEvent event)
			{
				window.destroy();
			}
		});

		MenuItem minimizeItem = new MenuItem(ILabels.Factory.getInstance().window_menu_minimize());
		minimizeItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler()
		{

			public void onClick (MenuItemClickEvent event)
			{
				window.minimize();
			}
		});

		MenuItem restoreItem = new MenuItem(ILabels.Factory.getInstance().window_menu_restore());
		restoreItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler()
		{

			public void onClick (MenuItemClickEvent event)
			{
				window.restore();
			}
		});

		MenuItem maximizeItem = new MenuItem(ILabels.Factory.getInstance().window_menu_maximize());
		maximizeItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler()
		{

			public void onClick (MenuItemClickEvent event)
			{
				window.maximize();
			}
		});
		menu.setItems(minimizeItem, restoreItem, maximizeItem, new MenuItemSeparator(), closeItem);

		return menu;
	}

}
