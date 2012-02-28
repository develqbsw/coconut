package sk.qbsw.sgwt.winnetou.client.ui.component.buttons;

import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

/**
 * Img button with rolling value feature
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CValueRollingImgButton extends ImgButton
{
	private int value = 0;

	public CValueRollingImgButton(final int maxValue)
	{
		this.addClickHandler(new ClickHandler()
		{

			public void onClick(ClickEvent event)
			{
				value = (value + 1) % maxValue;
			}
		});
	}

	/**
	 * Returns actual value of the button
	 * 
	 * @return int representation of value
	 */
	public int getValue()
	{
		return value;
	}

	
	public void reset()
	{
		setSrc(getSrc());
	}
}
