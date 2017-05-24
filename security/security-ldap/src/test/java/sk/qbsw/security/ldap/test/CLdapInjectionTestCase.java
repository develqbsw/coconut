package sk.qbsw.security.ldap.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sk.qbsw.security.ldap.service.CLDAPInjectionProtector;

/**
 * The Class CLdapInjectionTestCase.
 * 
 * @version 1.12.3
 * @since 1.12.3
 * @author Dalibor Rak
 */
public class CLdapInjectionTestCase {

	/**
	 * Test escape dn.
	 */
	@Test
	public void testEscapeDN() {
		assertEquals("No special characters to escape", "Helloé", CLDAPInjectionProtector.escapeDN("Helloé"));
		assertEquals("leading #", "\\# Helloé", CLDAPInjectionProtector.escapeDN("# Helloé"));
		assertEquals("leading space", "\\ Helloé", CLDAPInjectionProtector.escapeDN(" Helloé"));
		assertEquals("trailing space", "Helloé\\ ", CLDAPInjectionProtector.escapeDN("Helloé "));
		assertEquals("only 3 spaces", "\\  \\ ", CLDAPInjectionProtector.escapeDN("   "));
		assertEquals("Christmas Tree DN", "\\ Hello\\\\ \\+ \\, \\\"World\\\" \\;\\ ", CLDAPInjectionProtector.escapeDN(" Hello\\ + , \"World\" ; "));
	}

	/**
	 * Test escape search.
	 */
	@Test
	public void testEscapeSearch() {
		assertEquals("No special characters to escape", "Hi This is a test #çà", CLDAPInjectionProtector.escapeLDAPSearchFilter("Hi This is a test #çà"));
		assertEquals("LDAP Christams Tree", "Hi \\28This\\29 = is \\2a a \\5c test # ç à ô", CLDAPInjectionProtector.escapeLDAPSearchFilter("Hi (This) = is * a \\ test # ç à ô"));
	}
}
