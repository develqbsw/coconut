package sk.qbsw.core.model;

public class CArrayUtils
{
	public static String arrayToString (Object[] array) {
		if (array == null) {
			return "null";
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Object o : array) {
			if (!first) {
				sb.append(",");
			}
			sb.append(o.toString());
			first = false;
		}
		return sb.toString();
	}
}
