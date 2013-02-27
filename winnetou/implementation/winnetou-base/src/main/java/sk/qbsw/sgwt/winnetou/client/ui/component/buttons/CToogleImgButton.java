package sk.qbsw.sgwt.winnetou.client.ui.component.buttons;

import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

/**
 * Image with toogle feature.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CToogleImgButton extends ImgButton
{
	/**
	 * Constant identifies UP state
	 */
	public static final int STATE_UP = 0;
	/**
	 * Constant identifies DOWN state
	 */
	public static final int STATE_DOWN = 1;

	/**
	 * path to image up
	 */
	private String imgUp;
	/**
	 * path to image down
	 */
	private String imgDown;

	/**
	 * actial state of the component
	 */
	private int actualState = 0;

	public CToogleImgButton()
	{
		super();
		super.setActionType(SelectionType.BUTTON);

		setShowDisabled(true);
		setShowOverCanvas(false);
		setShowHover(false);
		setShowDown(false);
		setShowRollOver(false);
		setShowFocused(false);

		super.addClickHandler(new ClickHandler()
		{

			public void onClick(ClickEvent event)
			{
				actualState = (actualState + 1) % 2;
				setToogleState(actualState);
			}
		});
	}

	/**
	 * Sets image path for state up
	 * 
	 * @param path
	 */
	public void setStateUpImage(String path)
	{
		this.setSrc(path);
		this.imgUp = path;
	}

	/**
	 * Sets image path for state down
	 * 
	 * @param path
	 */
	public void setStateDownImage(String path)
	{
		this.imgDown = path;
	}

	/**
	 * Gets actual state
	 * 
	 * @return int. See STATE_UP and STATE_DOWN
	 */
	public int getToogleState()
	{
		return actualState;
	}

	/**
	 * Overrides original feature. When disabling button, change state to
	 * STATE_UP
	 */
	@Override
	public void setDisabled(boolean disabled)
	{
		if (disabled)
		{
			setToogleState(STATE_UP);
		}
		super.setDisabled(disabled);
	}

	/**
	 * Sest state (changes the icon)
	 * 
	 * @param state
	 *            state to set
	 */
	public void setToogleState(int state)
	{
		if (state == 0)
		{
			setSrc(imgUp);
		} else if (state == 1)
		{
			setSrc(imgDown);
		}
		actualState = state;
	}
}
