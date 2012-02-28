package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CImgItem;
import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemLabels;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.IMessages;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;

/**
 * Image of user
 * 
 * @author Dalibor Rak
 * @Version 0.1
 * @since 0.1
 * 
 */
public class CPhotoItem extends CImgItem
{
	public CPhotoItem()
	{
		super();
		setShowTitle(true);
		setTitle(ILabels.Factory.getInstance().item_photo());
		setHeight(110);
		setWidth(85);
		setSrc(null);
		
		super.setShowDisabled(false);
		
		// doubleclick for editing value
		super.image.addDoubleClickHandler(new DoubleClickHandler()
		{

			public void onDoubleClick(DoubleClickEvent event)
			{
				Dialog dialogProperties = new Dialog();
				dialogProperties.setWidth(300);

				SC.askforValue(ISystemLabels.Factory.getInstance().title_question(), IMessages.Factory.getInstance().item_enter_photo_path(), getSrc(), new ValueCallback()
				{

					public void execute(String value)
					{
						if (value != null)
						{
							setSrc(value);
						}
					}
				}, dialogProperties);
			}
		});
	}

	/**
	 * Sets image path, if null, no_photo.png will be loaded
	 * 
	 * @param path
	 *            path to set
	 */
	public void setSrc(String path)
	{
		if (path == null || path.length() == 0)
		{
			path = "photos/no_photo.png";
		}
		super.setSrc(path);
	}

}
