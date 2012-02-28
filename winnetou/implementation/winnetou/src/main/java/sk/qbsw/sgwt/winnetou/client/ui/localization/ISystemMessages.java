package sk.qbsw.sgwt.winnetou.client.ui.localization;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

/**
 * System messages stored in property file.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public interface ISystemMessages extends Messages
{
	public class Factory
	{
		private static ISystemMessages instance;

		public static ISystemMessages getInstance()
		{
			if (instance == null)
			{
				instance = GWT.create(ISystemMessages.class);
			}

			return instance;
		}
	}

	@Key("process.data_loading")
	public String data_loading();

	@Key("exceptions.system_exception_occured")
	public String system_exception_occured();

	@Key("security.user_not_logged_in")
	public String user_not_logged_in();

	@Key("ui.window.can_close")
	public String window_can_close();
	
	@Key("ui.table.select_something")
	public String table_select_something();
	
	@Key("ui.form.item.validation.email_bad_format")
	public String validation_email_bad_format();

	@Key("ui.form.item.validation.white_char_containing")
	public String validation_contains_white_chars();

	@Key("ui.form.item.validation.no_numbers_containing")
	public String validation_contains_not_numbers();
}
