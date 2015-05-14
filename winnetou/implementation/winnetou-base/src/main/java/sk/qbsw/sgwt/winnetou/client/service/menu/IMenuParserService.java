package sk.qbsw.sgwt.winnetou.client.service.menu;

import sk.qbsw.sgwt.winnetou.client.model.menu.CMenu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Menu parsing service
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
@RemoteServiceRelativePath ("rpc/winnetou/menu")
public interface IMenuParserService extends RemoteService
{
	public class Locator
	{
		private static IMenuParserServiceAsync instance;

		public static IMenuParserServiceAsync getInstance ()
		{
			if (instance == null)
			{
				instance = GWT.create(IMenuParserService.class);
			}
			return instance;
		}
	}

	public CMenu parse ();
}
