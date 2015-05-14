package sk.qbsw.sgwt.winnetou.client.model.menu;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * Menu items used to transfer between client and server
 * 
 * @author Dalibor Rak
 * @Version 0.1
 * @since 0.1
 *
 */
public class CMenu implements IsSerializable
{
	private ArrayList<CMenuTabItem> menuTabItems;

	public ArrayList<CMenuTabItem> getMenuTabItems ()
	{
		return menuTabItems;
	}

	public void setMenuTabItems (ArrayList<CMenuTabItem> menuTabItems)
	{
		this.menuTabItems = menuTabItems;
	}

}
