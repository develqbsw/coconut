package sk.qbsw.sgwt.winnetou.client.ui.component.panel.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sk.qbsw.sgwt.winnetou.client.model.menu.CMenuLabelsHolder;
import sk.qbsw.sgwt.winnetou.client.model.menu.CMenuTabItem;
import sk.qbsw.sgwt.winnetou.client.model.menu.CMenuTabSubItem;
import sk.qbsw.sgwt.winnetou.client.ui.component.IUIComponent;
import sk.qbsw.sgwt.winnetou.client.ui.component.buttons.CMenuImgButton;
import sk.qbsw.sgwt.winnetou.client.ui.component.buttons.CMenuImgButtonClickHandler;
import sk.qbsw.sgwt.winnetou.client.ui.component.menu.CCommandMenuItem;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuButton;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * Bar containing menu
 * 
 * @author Dalibor Rak
 * @version 0.1.2
 * @since 0.0.1
 */
public class CMenuBar extends ToolStrip implements IUIComponent
{
	// items
	private List<CMenuTabItem> menuItemTabs = new ArrayList<CMenuTabItem>();

	/**
	 * List of user's roles
	 */
	private List<String> userRoles;

	public CMenuBar ()
	{
		super();
		// services
	}

	public void constructMenu ()
	{
		createMenu();
		redraw();
	}

	/**
	 * Constructs menu (Ohh BORIS!!!)
	 * 
	 * @param userRoles
	 *            list of roles assigned to user
	 */
	private void createMenu ()
	{
		removeMembers();

		if (menuItemTabs == null)
		{
			return;
		}

		Menu menu = null;
		Iterator<CMenuTabItem> it = menuItemTabs.iterator();

		CMenuLabelsHolder menuLabels = CMenuLabelsHolder.getInstance();

		while (it.hasNext())
		{
			CMenuTabItem menuTabItem = (CMenuTabItem) it.next();
			if (menuTabItem.isEnabled())
			{
				ArrayList<CMenuTabSubItem> subItems = menuTabItem.getMenuTabSubItems();
				Iterator<CMenuTabSubItem> subIt = subItems.iterator();

				boolean shouldAddSeparator = false;
				boolean enteredItemBeforeSeparator = false;

				menu = new Menu();
				menu.setShowShadow(true);
				menu.setShadowDepth(10);

				while (subIt.hasNext())
				{
					// TAB LINKS
					CMenuTabSubItem menuTabSubItem = subIt.next();

					if (menuTabSubItem.isEnabled())
					{

						CCommandMenuItem menuItem = null;
						if (menuTabSubItem.isSeparator())
						{
							shouldAddSeparator = true;
						}
						else
						{
							String tmpLabel = "";

							if (menuTabSubItem.getLabel() != null)
							{
								tmpLabel += menuTabSubItem.getLabel();
							}
							else
							{
								tmpLabel += menuLabels.getLabel(menuTabSubItem.getLabelKey());
							}
							// label + url
							menuItem = new CCommandMenuItem();
							menuItem.setSubItem(menuTabSubItem);
							menuItem.setTitle(tmpLabel);

							String iconPath = menuTabSubItem.getIconPath();
							if (iconPath != null)
							{
								menuItem.setIcon(iconPath);
							}

							String[] itemRoles = menuTabSubItem.getRoles();
							if (userContainsAtLeastOne(itemRoles))
							{
								menuItem.setEnabled(true);
							}
							else
							{
								menuItem.setEnabled(false);
							}
						}
						if (null != menuItem && menuItem.getEnabled())
						{
							if (shouldAddSeparator)
							{
								if (enteredItemBeforeSeparator)
								{
									menu.addItem(new MenuItemSeparator());
								}
								shouldAddSeparator = false;
							}
							menu.addItem(menuItem);
							enteredItemBeforeSeparator = true;
						}
					}
				}

				// zatial to potrebujeme mat, ale je to cista blbost
				@SuppressWarnings ("unused")
				MenuButton menuButton = new MenuButton(menuTabItem.getLabel(), menu);

				String hint = "";
				if (menuTabItem.getHint()!= null)
				{
					hint = menuTabItem.getHint();
				}
				else
				{
					hint = menuLabels.getLabel(menuTabItem.getHintKey());
				}
				
				
				CMenuImgButton imgButton = new CMenuImgButton(menuTabItem.getIconPath(), hint);
				imgButton.addClickHandler(new CMenuImgButtonClickHandler(menu));

				if (menu.getItems().length != 0)
				{
					this.addMember(imgButton);
				}
			}
		}
	}

	/**
	 * Nothing to init
	 */
	public void initComponents ()
	{
	}

	/**
	 * @See {@link IUIComponent#initLayout()}
	 */
	public void initLayout ()
	{
		this.setStyleName("menuBar");

		this.setWidth("*");
		this.setHeight(74);

		this.setAlign(Alignment.LEFT);
		this.setPadding(15);
		this.setMembersMargin(10);
	}

	private void removeMembers ()
	{
		for (Canvas member : getMembers())
		{
			this.removeMember(member);
		}
	}

	public void setMenuModel (List<CMenuTabItem> menuItemTabs)
	{
		this.menuItemTabs = menuItemTabs;
	}

	/**
	 * Sets user's roles
	 * 
	 * @param userRoles
	 */
	public void setUserRoles (List<String> userRoles)
	{
		this.userRoles = userRoles;
	}

	/**
	 * Sets user's roles (converts them to strings)
	 * 
	 * @param userRoles
	 */
	public void setUserRolesIds (List<Long> userRoles)
	{
		ArrayList<String> retVals = new ArrayList<String>();

		for (Long role : userRoles)
		{
			retVals.add(role.toString());
		}

		this.userRoles = retVals;
	}

	/**
	 * Checks if user has at least one role
	 * 
	 * @param itemRoles
	 * @param userRoles
	 * @return true/false
	 */
	private boolean userContainsAtLeastOne (String[] itemRoles)
	{
		if (itemRoles == null || itemRoles.length == 0)
		{
			return true;
		}

		for (int i = 0; i < itemRoles.length; i++)
		{
			if (userRoles.contains(itemRoles[i]))
			{
				return true;
			}
		}
		return false;
	}

}
