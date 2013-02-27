package sk.qbsw.sgwt.winnetou.client.ui.component.screen;

import java.util.HashMap;

import sk.qbsw.sgwt.winnetou.client.exception.CSystemFailureException;
import sk.qbsw.sgwt.winnetou.client.ui.component.window.IWindow;

/**
 * Holder for screens factories (Can be used fo instantiation of screen by it's
 * ID)
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
@SuppressWarnings("serial")
public class CScreenHolder extends HashMap<String, IScreenFactory>
{

	/**
	 * singleton's instance
	 */
	private static CScreenHolder instance;

	/**
	 * getter for instance
	 * 
	 * @return
	 */
	public static CScreenHolder getInstance()
	{
		if (instance == null)
		{
			instance = new CScreenHolder();
		}

		return instance;
	}

	/**
	 * No one is able to create the instance. Use
	 * {@link CScreenHolder#getInstance()}
	 */
	private CScreenHolder()
	{
	}

	/**
	 * gets Screen by it's ID
	 * 
	 * @param id
	 *            id of factory
	 * @return screen
	 */
	public IScreen getScreen(String id)
	{
		if (!containsKey(id))
		{
			throw new CSystemFailureException("No screen for id=" + id, null);
		}

		return get(id).getScreen();
	}

	/**
	 * gets Window by it's ID
	 * 
	 * @param id
	 *            id of factory
	 * @return window
	 */
	public IWindow getWindow(String id)
	{
		if (!containsKey(id))
		{
			throw new CSystemFailureException("No screen for id=" + id, null);
		}

		return get(id).getWindow();
	}

	/**
	 * Pust factory to the holder
	 * 
	 * @param factory
	 */
	public void put(IScreenFactory factory)
	{
		put(factory.getScreenId(), factory);
	}
}
