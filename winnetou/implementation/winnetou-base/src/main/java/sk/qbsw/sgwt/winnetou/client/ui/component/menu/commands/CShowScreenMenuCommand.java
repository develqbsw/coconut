package sk.qbsw.sgwt.winnetou.client.ui.component.menu.commands;

import sk.qbsw.sgwt.winnetou.client.model.menu.CMenuTabSubItem;
import sk.qbsw.sgwt.winnetou.client.ui.component.window.CAppWindowManager;

/**
 * Menu command responsible for showing screens
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CShowScreenMenuCommand implements IMenuCommand
{

	public static final String ID = "SHOW_SCREEN";

	public void execute(CMenuTabSubItem menuSubItem)
	{
		CAppWindowManager.createWindow(menuSubItem.getId());
	}

	public String getId()
	{
		return ID;
	}

}
