package sk.qbsw.sgwt.winnetou.client.ui.component.window;

import java.util.ArrayList;
import java.util.List;

import sk.qbsw.sgwt.winnetou.client.ui.component.panel.CPanelBaseFactory;
import sk.qbsw.sgwt.winnetou.client.ui.component.screen.CScreenHolder;
import sk.qbsw.sgwt.winnetou.client.ui.component.screen.IScreen;

import com.smartgwt.client.widgets.Window;

/**
 * Manager for creating windows after click from menu
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CAppWindowManager
{
	/**
	 * Count of windows
	 */
	private static int countWindows = 0;

	/**
	 * Default padding from top left corner
	 */
	private final static int DEFAULT_PADDING = 10;

	/**
	 * Max windows in one layer
	 */
	private static int maxWindowsInLayer = -1;

	/**
	 * Padding for each item in level
	 */
	private static int padding = 30;

	/**
	 * Window position in layer
	 */
	private static int windowPosition = 0;

	/**
	 * Creates window
	 * 
	 * @param screen
	 *            screen to include into the window
	 */
	public static Window createWindow (IScreen screen, IWindow window)
	{
		IWindow newWindow = null;

		if (window != null)
		{
			newWindow = window;
		}
		else
		{
			newWindow = windowFactory.generateWindow();
		}

		screen.initLayout();
		screen.initComponents();
		screen.showScreen();

		newWindow.initLayout();
		newWindow.prepareHeaderControls(screen);
		newWindow.initComponents();
		newWindow.prepareToolbarControls(screen);

		newWindow.addItem(screen);
		newWindow.getWindow().setStatus(screen.getScreenId());
		newWindow.getWindow().show();

		CPanelBaseFactory.getWorkPanel().addChild(newWindow.getWindow());

		newWindow.getWindow().setStatus(screen.getScreenId());

		reposition(newWindow, windowPosition);

		screen.loadData();
		screen.autoFocus();

		return newWindow.getWindow();
	}

	/**
	 * Creates window
	 * 
	 * @param screenId
	 *            screen to include in the window
	 */
	public static Window createWindow (String screenId)
	{
		CScreenHolder screens = CScreenHolder.getInstance();
		IWindow changer = screens.getWindow(screenId);
		IScreen screen = screens.getScreen(screenId);

		return createWindow(screen, changer);
	}

	/**
	 * Repositions window to counted position
	 * 
	 * @param window window to reposition
	 * @param index index of window
	 */
	public static void reposition (IWindow window, int index)
	{
		int corrected = index - 1;

		int x = corrected * padding + DEFAULT_PADDING;
		int y = corrected * padding + DEFAULT_PADDING;

		window.getWindow().setLeft(x);
		window.getWindow().setTop(y);
	}

	/**
	 * Initializes...
	 * @param maxWindowsInLayer number of windows in layer
	 * @param padding padding for each item in layer
	 */
	public static void initialize (int maxWindowsInLayer, int padding)
	{
		CAppWindowManager.maxWindowsInLayer = maxWindowsInLayer;
		CAppWindowManager.padding = padding;
	}

	/**
	 * Counts new window
	 */
	public static void windowCreated ()
	{
		countWindows++;
		windowPosition++;

		// clears window position
		if (windowPosition == maxWindowsInLayer && maxWindowsInLayer > -1)
		{
			windowPosition = 1;
		}
	}

	/**
	 * Called when the window is destroyed
	 */
	public static void windowDestroyed ()
	{
		countWindows--;
		if (countWindows == 0)
		{
			windowPosition = 0;
		}
	}

	/**
	 * Factory for generating default window
	 */
	private static IWindowFactory windowFactory = new CDefaultAppWindowFactory();

	/**
	 * Sets window factory, which is used when no window is defined from caller
	 * @param factory
	 */
	public static void setWindowFactory (IWindowFactory factory)
	{
		windowFactory = factory;
	}

	/**
	 * Remembers all chart windows
	 */
	private List<Window> chartWindows;

	/**
	 * Registers chart window
	 * @param window
	 */
	public void registerChartWindow (Window window)
	{
		if (chartWindows == null)
		{
			chartWindows = new ArrayList<Window>();
		}

		chartWindows.add(window);
	}

	/**
	 * closes all chart window
	 * @param window
	 */
	public void closeChartWindows ()
	{
		for (Window window : chartWindows)
		{
			if (window != null && window.isAttached() && window.isDrawn())
			{
				window.destroy();
			}
		}
	}
}
