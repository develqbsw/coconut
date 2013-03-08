package sk.qbsw.indy.base.components.image;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.Model;

/**
 * Reads external image from path.
 *
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 */
public class CExternalImage extends WebComponent
{

	/** Serialization version id. */
	private static final long serialVersionUID = 1L;


	/**
	 * Instantiates a new c external image.
	 *
	 * @param id the id
	 * @param imageUrl the image url
	 */
	public CExternalImage (String id, String imageUrl)
	{
		super(id);
		add(AttributeModifier.replace("src", new Model<String>(imageUrl)));
		setVisible(! (imageUrl == null || imageUrl.equals("")));
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#onComponentTag(org.apache.wicket.markup.ComponentTag)
	 */
	@Override
	protected void onComponentTag (ComponentTag tag)
	{
		super.onComponentTag(tag);
		checkComponentTag(tag, "img");
	}

}
