package sk.qbsw.sgwt.winnetou.client.ui.component.form.item;

import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.form.fields.CanvasItem;

/**
 * Image form item (embeded in CanvasItem)
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CImgItem extends CanvasItem
{
	/**
	 * Internal image
	 */
	protected Img image;

	public CImgItem()
	{
		super();
		image = new Img();
		image.setStyleName("imgItem");
		setCanvas(image);
	}

	public CImgItem(String name)
	{
		super(name);
		image = new Img();
		image.setStyleName("imgItem");
		setCanvas(image);
	}
	
	
	/**
	 * Sets image path
	 * 
	 * @param path
	 *            path to set
	 */
	public void setSrc(String path)
	{
		image.setSrc(path);
	}

	/**
	 * Gets path of shown image
	 * 
	 * @return
	 */
	public String getSrc()
	{
		return image.getSrc();
	}

	@Override
	public void setShowDisabled(Boolean disabled)
	{
		super.setShowDisabled(disabled);
		if (image != null)
		{
			this.image.setShowDisabled(disabled);
		}
	}
}
