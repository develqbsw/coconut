package sk.qbsw.sgwt.winnetou.client.ui.component.buttons;

import com.smartgwt.client.widgets.ImgButton;


/**
 * Image represented as button with command feature
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CMenuImgButton extends ImgButton
{
	/**
	 * Image constructor
	 * @param iconPath path of icon file
	 * @param hint hint for the image
	 */
	public CMenuImgButton(String iconPath, String hint)
	{
		super();
		setWidth(64);
		setHeight(64);
		setShowRollOver(false);
		setShowDown(false);
		setShowFocused(false);
		setSrc(iconPath);
		setTooltip(hint);
		setBorder("1px solid black");
		
		setShowShadow(true);
		setShadowDepth(5);
		setShadowOffset(5);
		setShadowSoftness(5);
	}	
}
