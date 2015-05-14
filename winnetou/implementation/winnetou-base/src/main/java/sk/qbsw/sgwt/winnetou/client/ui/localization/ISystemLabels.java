package sk.qbsw.sgwt.winnetou.client.ui.localization;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

/**
 * System labels stored in property file.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public interface ISystemLabels extends Messages
{
	public class Factory
	{
		private static ISystemLabels instance;

		public static ISystemLabels getInstance()
		{
			if (instance == null)
			{
				instance = GWT.create(ISystemLabels.class);
			}

			return instance;
		}
	}

	@Key("title.info")
	public String title_info();

	@Key("title.processing")
	public String title_processing();

	@Key("title.warning")
	public String title_warning();

	@Key("title.question")
	public String title_question();

	@Key("title.details")
	public String title_details();

	@Key("title.add")
	public String title_add();

	@Key("title.modify")
	public String title_modify();
	
	@Key("label.yes")
	public String label_yes();
	
	@Key("label.no")
	public String label_no();
}
