package sk.qbsw.core.image.model;

import java.io.Serializable;

/**
 * The image size. 
 *
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public class ImageSize implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7069368586384029288L;

	/** The width. */
	private final int width;

	/** The height. */
	private final int height;

	/**
	 * Instantiates a new image size.
	 *
	 * @param width the width
	 * @param height the height
	 */
	public ImageSize (int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	/**
	 * Instantiates a new image size.
	 *
	 * @param value the value
	 * @param dimension the dimension
	 */
	public ImageSize (int value, Dimension dimension)
	{
		if (Dimension.WIDTH.equals(dimension))
		{
			this.width = value;
			this.height = 0;
		}
		else
		{
			this.width = 0;
			this.height = value;
		}
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth ()
	{
		return width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight ()
	{
		return height;
	}

	/**
	 * The Enum Dimension.
	 */
	public enum Dimension
	{
		/** The width. */
		WIDTH,

		/** The height. */
		HEIGHT;
	}
}
