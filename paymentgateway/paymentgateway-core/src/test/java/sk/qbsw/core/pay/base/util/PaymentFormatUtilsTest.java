package sk.qbsw.core.pay.base.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class PaymentFormatUtilsTest {

	@Test
	public void testFormatVS() {
		
	    assertEquals("0000000010",PaymentFormatUtils.formatVS(10L));
	    assertEquals("1000000000",PaymentFormatUtils.formatVS(1000000000L));
	    assertEquals("0000000000",PaymentFormatUtils.formatVS(0L));
	}

	@Test
	public void testFormatSS() {
		assertEquals("0000000001",PaymentFormatUtils.formatSS(1L));
		assertEquals("0000000000",PaymentFormatUtils.formatSS(0L));
	}

	@Test
	public void testFormatKS() {
		assertEquals("0000",PaymentFormatUtils.formatKS(0L));
		assertEquals("0001",PaymentFormatUtils.formatKS(1L));
	}

}
