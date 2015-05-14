package sk.qbsw.core.logging.aspect.param;

/**
 * Class represent one parameter which come to method
 * This class not log parameter
 * 
 * @author Michal Lacko
 * @author Marián Oravec
 * @since 1.8.0
 * @version 1.8.0
 */
public class CNotLoggedParameter extends AParameter {

	public static final String NOT_LOGGED_VALUE = "<not-logged>";

	public CNotLoggedParameter (String name) {
		super(name);
	}

	@Override
	public String toString () {
		final StringBuilder sb = new StringBuilder();

		sb.append(this.name);
		sb.append("=");
		sb.append(NOT_LOGGED_VALUE);

		return sb.toString();
	}

}
