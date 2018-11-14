package sk.qbsw.security.authentication.ldap.test;

import org.junit.Test;
import sk.qbsw.security.authentication.ldap.provider.LDAPInjectionProtector;

import static org.junit.Assert.assertEquals;

/**
 * The ldap injection test case.
 *
 * @author Dalibor Rak
 * @version 2.0.0
 * @since 1.12.3
 */
public class LdapInjectionTestCase
{
	/**
	 * Test escape dn.
	 */
	@Test
	public void testEscapeDN ()
	{
		assertEquals("No special characters to escape", "Helloé", LDAPInjectionProtector.escapeDN("Helloé"));
		assertEquals("leading #", "\\# Helloé", LDAPInjectionProtector.escapeDN("# Helloé"));
		assertEquals("leading space", "\\ Helloé", LDAPInjectionProtector.escapeDN(" Helloé"));
		assertEquals("trailing space", "Helloé\\ ", LDAPInjectionProtector.escapeDN("Helloé "));
		assertEquals("only 3 spaces", "\\  \\ ", LDAPInjectionProtector.escapeDN("   "));
		assertEquals("Christmas Tree DN", "\\ Hello\\\\ \\+ \\, \\\"World\\\" \\;\\ ", LDAPInjectionProtector.escapeDN(" Hello\\ + , \"World\" ; "));
	}

	/**
	 * Test escape search.
	 */
	@Test
	public void testEscapeSearch ()
	{
		assertEquals("No special characters to escape", "Hi This is a test #çà", LDAPInjectionProtector.escapeLDAPSearchFilter("Hi This is a test #çà"));
		assertEquals("LDAP Christams Tree", "Hi \\28This\\29 = is \\2a a \\5c test # ç à ô", LDAPInjectionProtector.escapeLDAPSearchFilter("Hi (This) = is * a \\ test # ç à ô"));
	}
}
