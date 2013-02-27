package sk.qbsw.sgwt.winnetou.client.ui.component.screen;

import sk.qbsw.sgwt.winnetou.client.ui.component.window.IWindow;

/**
 * Base Factory for all screens
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public abstract class AScreenFactory implements IScreenFactory
{
	/**
	 * id of the screen
	 */
	private String id;

	/**
	 * instance of the screen
	 */
	private IScreen screenInstance;

	protected AScreenFactory(String id)
	{
		this.id = id;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see sk.qbsw.ondrej.application.client.screens.IScreenFactory#getScreen()
	 */
	public IScreen getScreen()
	{
		// if (screenInstance == null)
		// {
		screenInstance = getSingleScreenInstance();
		// }

		return screenInstance;
	}

	public String getScreenId()
	{
		return this.id;
	}

	protected abstract IScreen getSingleScreenInstance();

	public IWindow getWindow()
	{
		return null;
	}
}
