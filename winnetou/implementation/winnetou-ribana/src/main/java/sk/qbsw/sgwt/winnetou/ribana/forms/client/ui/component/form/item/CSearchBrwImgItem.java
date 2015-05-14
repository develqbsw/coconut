package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CImgItem;

import com.smartgwt.client.widgets.events.ClickHandler;

/**
 * Item used for execute search in tree
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CSearchBrwImgItem extends CImgItem
{
	private final static String ICON_REFRESH = "buttons/refresh.png";

	public CSearchBrwImgItem()
	{
		super();
		setShowTitle(false);
		setHeight(20);
		setWidth(20);
		setDisabled(false);
		setSrc(ICON_REFRESH);
	}

	/**
	 * Adds click handler to the image
	 */
	public void addImageClickHandler(ClickHandler handler)
	{
		super.image.addClickHandler(handler);
	}
}
