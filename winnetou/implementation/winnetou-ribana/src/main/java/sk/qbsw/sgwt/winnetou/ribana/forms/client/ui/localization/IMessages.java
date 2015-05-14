package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface IMessages extends Messages
{
	public class Factory
	{
		private static IMessages instance;

		public static IMessages getInstance ()
		{
			if (instance == null)
			{
				instance = GWT.create(IMessages.class);
			}

			return instance;
		}
	}

	@Key ("registration.accept_processing")
	public String registration_accept_processing ();

	@Key ("registration.accept_licence")
	public String registration_accept_licence ();

	@Key ("registration.passwords_not_matching")
	public String registration_passwords_not_matching ();

	@Key ("item.enter_photo_path")
	public String item_enter_photo_path ();

	@Key ("user.main_deactivated")
	public String user_main_deactivated ();

	@Key ("city.wrong_value")
	public String city_wrong_value ();

	@Key ("request.too_much_work_days")
	public String request_too_much_work_days ();

	@Key ("activity.wrong_order")
	public String activity_wrong_order ();

	@Key ("registration.bad_password_format")
	public String registration_bad_password_format ();


}
