package sk.qbsw.sgwt.winnetou.client.ui.component.menu.commands;

import sk.qbsw.sgwt.winnetou.client.model.menu.CMenuTabSubItem;

/**
 * Command component for executing menu action
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public interface IMenuCommand
{
	/**
	 * Executes action on menu item
	 */
	public void execute(CMenuTabSubItem menuSubItem);
	
	/**
	 * Gets command identifier (is used for searching of command)
	 * @return id of the command
	 */
	public String getId();
}


