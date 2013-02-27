package sk.qbsw.sgwt.winnetou.client.ui.component.window.information;

import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemLabels;
import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemMessages;

import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * Window used to represent actual processing status of operation
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CProcessingInfoWindow extends Window
{
	private static CProcessingInfoWindow instance;

	public static CProcessingInfoWindow getInstance()
	{
		if (instance == null)
		{
			instance = new CProcessingInfoWindow();
		}
		return instance;
	}

	public static CProcessingInfoWindow getNewInstance()
	{
		return new CProcessingInfoWindow();
	}

	public static CProcessingInfoWindow getNewInstance(Canvas parent)
	{
		CProcessingInfoWindow retVal = new CProcessingInfoWindow();
		retVal.setParentElement(parent);

		return retVal;
	}

	private Img image;

	private CProcessingInfoWindow()
	{
		initLayout();
		initComponents();
	}

	/**
	 * Hides the window
	 */
	public void hideMessage()
	{
		hide();
	}

	protected void initComponents()
	{
		HLayout layout = new HLayout();
		layout.setMembersMargin(5);
		layout.setHeight100();
		layout.setWidth100();

		image = new Img("status/ajax.gif", 32, 32);
		image.setImageType(ImageStyle.CENTER);
		image.setLeft(10);
		image.setTop(10);

		Canvas imgCanvas = new Canvas();
		imgCanvas.addChild(image);

		layout.addMember(imgCanvas);
		layout.addMember(new Label(ISystemMessages.Factory.getInstance().data_loading()));

		this.addItem(layout);
	}

	protected void initLayout()
	{
		this.setShowCloseButton(false);
		this.setShowMinimizeButton(false);
		this.setShowMaximizeButton(false);
		this.setTitle(ISystemLabels.Factory.getInstance().title_processing());
		this.setWidth(160);
		this.setHeight(80);
	}

	/**
	 * Shows message in the center of the page
	 * 
	 * @param message
	 *            message to show
	 */
	public void showMessage(String message)
	{
		show();
		centerInPage();
	}

	/**
	 * Shows message
	 * 
	 * @param message
	 *            message to show
	 * @param parent
	 *            parent object (used for centering)
	 */
	public void showMessage(String message, Canvas parent)
	{
		setParentElement(parent);
		show();
		centerInPage();
	}
}
