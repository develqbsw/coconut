package sk.qbsw.sgwt.winnetou.client.service.menu;

import sk.qbsw.sgwt.winnetou.client.model.menu.CMenu;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IMenuParserServiceAsync
{
	public void parse(AsyncCallback<CMenu> callback);
}
