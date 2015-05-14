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
public class CSearchImgItem extends CImgItem
{
	
	private final static String ICON_DETAILS = "buttons/view.png";

	public CSearchImgItem()
	{
		super();

		setShowTitle(false);
		setHeight(20);
		setWidth(20);
		setSrc(ICON_DETAILS);
	}
	
	/**
	 * Adds click handler to the image
	 */
	public void addClick(ClickHandler handler)
	{
		super.image.addClickHandler(handler);
	}
}
