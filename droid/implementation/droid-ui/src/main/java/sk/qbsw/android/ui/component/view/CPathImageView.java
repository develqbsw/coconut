package sk.qbsw.android.ui.component.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Class represents gallery image view - it's normal image view + path to actual image on disk
 * @author Tomas Lauro
 *
 */
public class CPathImageView extends ImageView
{
	private String imagePath;

	public CPathImageView (Context context)
	{
		super(context);
	}

	public CPathImageView (Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public CPathImageView (Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public String getImagePath ()
	{
		return imagePath;
	}

	public void setImagePath (String imagePath)
	{
		this.imagePath = imagePath;
	}
}
