package sk.qbsw.indy.base.atributemodifier;

/**
 * Class to remove specific CSS class from HTML component
 * Usage: Component.add(new CssClassRemover("myStyle"));
 * 
 * @author Roman Farkas
 * @author Dalibor Rak
 * 
 * @version 1.3.0
 * @since 1.3.0
 */
public class CCSSClassRemoveModifier extends CHTMLAttributeRemoveModifier{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new cCSS class remove modifier.
	 *
	 * @param valueToRemove the value to remove
	 */
	public CCSSClassRemoveModifier (String valueToRemove)
	{
		super("class", valueToRemove);
	}
}