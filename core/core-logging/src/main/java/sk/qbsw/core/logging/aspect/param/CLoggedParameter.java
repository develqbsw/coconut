package sk.qbsw.core.logging.aspect.param;

/**
 * Class represent one parameter which come to method
 * This class log parameter
 * 
 * @author Michal Lacko
 * @author Marián Oravec
 * @since 1.8.0
 * @version 1.8.0
 */
public class CLoggedParameter extends AParameter {

	protected final Object value;

	public CLoggedParameter (String name, Object value) {
		super(name);

		this.value = value;
	}

	@Override
	public String toString () {
		final StringBuilder sb = new StringBuilder();

		sb.append(this.name);
		sb.append("=");

		if (this.value == null) {
			sb.append("<null>");
		} else {
			sb.append("'").append(this.value).append("'");
		}

		return sb.toString();
	}

}
