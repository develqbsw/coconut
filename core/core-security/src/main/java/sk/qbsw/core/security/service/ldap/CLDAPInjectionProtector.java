package sk.qbsw.core.security.service.ldap;

/**
 * LDAP injection protector
 * 
 * @author Dalibor Rak
 * @version 1.12.3
 * @since 1.12.3
 */
public class CLDAPInjectionProtector {

	/**
	 * Escape dn.
	 *
	 * @param name
	 *            the name
	 * @return the string
	 */
	public static String escapeDN(String name) {
		StringBuilder sb = new StringBuilder();
		if ((name.length() > 0)
				&& ((name.charAt(0) == ' ') || (name.charAt(0) == '#'))) {
			sb.append('\\'); // add the leading backslash if needed
		}
		for (int i = 0; i < name.length(); i++) {
			char curChar = name.charAt(i);
			switch (curChar) {
			case '\\':
				sb.append("\\\\");
				break;
			case ',':
				sb.append("\\,");
				break;
			case '+':
				sb.append("\\+");
				break;
			case '"':
				sb.append("\\\"");
				break;
			case '<':
				sb.append("\\<");
				break;
			case '>':
				sb.append("\\>");
				break;
			case ';':
				sb.append("\\;");
				break;
			default:
				sb.append(curChar);
			}
		}
		if ((name.length() > 1) && (name.charAt(name.length() - 1) == ' ')) {
			sb.insert(sb.length() - 1, '\\'); // add the trailing backslash if
												// needed
		}
		return sb.toString();
	}

	/**
	 * Escape ldap search filter.
	 *
	 * @param filter
	 *            the filter
	 * @return the string
	 */
	public static final String escapeLDAPSearchFilter(String filter) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < filter.length(); i++) {
			char curChar = filter.charAt(i);
			switch (curChar) {
			case '\\':
				sb.append("\\5c");
				break;
			case '*':
				sb.append("\\2a");
				break;
			case '(':
				sb.append("\\28");
				break;
			case ')':
				sb.append("\\29");
				break;
			case '\u0000':
				sb.append("\\00");
				break;
			default:
				sb.append(curChar);
			}
		}
		return sb.toString();
	}
}
