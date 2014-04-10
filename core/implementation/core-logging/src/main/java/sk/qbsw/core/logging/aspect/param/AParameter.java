package sk.qbsw.core.logging.aspect.param;

/**
 * Abstract parameter which handle parameter name
 * 
 * @author Marián Oravec
 * @since 1.8.0
 * @version 1.8.0
 */
public abstract class AParameter {

	protected final String name;

	protected AParameter (String name) {
		this.name = name;
	}

}
